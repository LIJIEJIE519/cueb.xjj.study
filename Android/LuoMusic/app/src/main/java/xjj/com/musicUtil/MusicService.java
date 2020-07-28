package xjj.com.musicUtil;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import xjj.com.luomusic.R;

/**
 * Created by XJJ on 2018/11/27.
 * 播放音乐的后台--服务--广播
 */

public class MusicService extends Service {

    private List<Music> musicList;      // 保存音乐列表
    private int position;                // 当前音乐的序号
    private MediaPlayer mediaPlayer;    // 音乐播放器
    private ActivityReceiver activityReceiver;  // 广播接收器
    private Music currentMusic;         // 当前播放的音乐
    private Timer timer;                // 定时器
    private int listType;               // 列表的类型
    private String styleString = Constants.LIST_LOOP;   // 默认列表循环

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        activityReceiver = new ActivityReceiver();
        // 动态创建广播--一定需要取消注册
        IntentFilter filter = new IntentFilter();   // 广播过滤器
        // 添加监听
        filter.addAction(Constants.CONTROL_ACTION); //控制音乐播放的动作
        filter.addAction(Constants.SEEKBAR_ACTION); //控制音乐播放的进度
        filter.addAction(Constants.UPDATE_STYLE);   //控制音乐播放的形式
        // 注册广播接收器
        registerReceiver(activityReceiver, filter);
        super.onCreate();
    }
    // 内部类动态注册广播，获取前台发送的广播
    private class ActivityReceiver extends BroadcastReceiver {
        // 必须实现的方法--通过Intent传送广播
        @Override
        public void onReceive(Context context, Intent intent) {
            // 接收控制播放的广播--新音乐，播放，暂停
            Log.d("position","ActivityReceiver");
            if (intent.getAction() == Constants.CONTROL_ACTION) {
                int isNew = intent.getIntExtra("new", -1);      // 如果没有返回-1
                if (isNew != -1) {      // 播放新音乐,默认从所有音乐中开始播放
                    listType = intent.getIntExtra("listType", Constants.ALL_MUSIC);
                    position = intent.getIntExtra("position", 0);   //获得播放序号
                    if (listType == Constants.ALL_MUSIC) {
                        // 从所有音乐里播放
                        musicList = Constants.musicList;
                    }else if (listType == Constants.PLAY_MUSIC){
                        // 从播放列表里播放
                        musicList = Constants.playlist;
                    }else if (listType == Constants.WEB_MUSIC){
                        // 网络音乐
                        musicList = Constants.webMusicList;
                    }
                    currentMusic = musicList.get(position);
                    // 准备并播放音乐
                    preparedAndPlay(currentMusic);
                }else {
                    int control = intent.getIntExtra("control", -1);
                    if (control == Constants.PAUSE) {
                        // 表示暂停音乐
                        mediaPlayer.pause();    // 暂停音乐并取消定时器
                        Constants.isPlay = 0;
                        timer.cancel();
                    }else if (control == Constants.PLAY) {
                        // 播放音乐
                        mediaPlayer.start();    // 播放音乐启动定时器
                        Constants.isPlay = 1;
                        startTimer();
                    }
                }
            }else if (intent.getAction() == Constants.SEEKBAR_ACTION){
                // 否则是进度条的广播
                int progress = intent.getIntExtra("progress", 0);   // 获取传递的进度
                // 将进度转换为相应时间位置
                int position = (int)(currentMusic.getTime() * progress * 1.0/100);
                // 将音乐调转到指定位置
                mediaPlayer.seekTo(position);
            }else if (intent.getAction() == Constants.UPDATE_STYLE) {
                // 否则为更新播放模式
                styleString = intent.getStringExtra("style");   // 获取播放模式类型
                //Context.MODE_PRIVATE 为默认操作模式，代表该文件是私有数据，只能被应用本身访问，
                //在该模式下，写入的内容会覆盖原文件的内容。
                SharedPreferences musicPreferences = getSharedPreferences("music", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = musicPreferences.edit();      // 获取参数编辑器
                editor.putString("style", styleString);     // 保存播放模式类型
                editor.commit();    // 提交数据
            }
        }
    }
    // 准备并播放音乐
    public void preparedAndPlay(final Music music) {
        try{
            mediaPlayer.reset();    // 重置媒体播放器
            mediaPlayer.setDataSource(music.getUrl());  // 设置音乐的路径
            mediaPlayer.prepare();  // 准备播放音乐
            mediaPlayer.start();    // 播放音乐
            startTimer();           // 开启计时器
            sendNotification();     // 发送广播
            Constants.isPlay = 1;
            Constants.currentPosition = position;
            Constants.playlist.add(music);
            saveInfo();             // 保存数据
            // 音乐播放结束监听
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.d("position","onCompletion");
                    // 如果是列表循环播放
                    if (Constants.LIST_LOOP.equalsIgnoreCase(styleString)) {
                        // 播放下一首
                        position = (position+1)%musicList.size();
                    }
                    // 如果是单曲循环position不变
                    currentMusic = musicList.get(position);     // 获取下一首音乐
                    preparedAndPlay(currentMusic);
                    // 动态生成广播
                    Intent intent = new Intent(Constants.COMPLETE_ACTION);
                    intent.putExtra("position", position);
                    // 发送广播
                    sendBroadcast(intent);
                }
            });
            // 因为直接切歌会发生错误，所以增加错误监听器。返回true。就不会回调onCompletion方法了。
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    return true;
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    // 存储类型及音乐序号信息
    public void saveInfo() {
        SharedPreferences musicPreferences = getSharedPreferences("music", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musicPreferences.edit();
        editor.putInt("listType", listType);        // 保存音乐列表类型
        editor.putInt("position", position);        // 保存音乐的位置
        editor.commit();    // 提交数据
    }
    //发送小通知
    public void sendNotification() {
        // 获取通知服务器
        NotificationManager manager = (NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);  //通知构建器
        builder.setAutoCancel(false)
                .setTicker("乐音乐")
                .setSmallIcon(R.drawable.music)
                .setContentTitle("正在播放音乐")
                .setContentText(currentMusic.getName()+"  "+currentMusic.getSinger());

        Intent intent = new Intent("com.xjj.music_play");
        // 通知待确定的Intent
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pIntent);
        manager.notify(0x11, builder.build());      // 发送通知
    }
    // 定时器
    public void startTimer() {
        // 定时发送广播， 通知前台更新进度条
        timer = new Timer();
        timer.schedule(new TimerTask() {    // 创建定时执行的任务
            @Override
            public void run() {
                Intent updateIntent = new Intent(Constants.UPDATE_ACTION);
                // 获得当前音乐的播放进度
                updateIntent.putExtra("position", mediaPlayer.getCurrentPosition());
                sendBroadcast(updateIntent);
            }
        }, 0,1000);       // 每隔1s发一次
    }
    // 销毁广播
    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            // 销毁时重置音乐播放器
            mediaPlayer.reset();
        }
        if (activityReceiver != null) {
            // 取消广播接收器
            unregisterReceiver(activityReceiver);
        }
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
