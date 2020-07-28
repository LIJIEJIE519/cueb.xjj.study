package xjj.com.luomusic;

import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import xjj.com.WebUtil.LoadImg;
import xjj.com.musicUtil.Constants;
import xjj.com.musicUtil.Music;
import xjj.com.musicUtil.MusicUtils;
/*
* 音乐播放页面
* */
public class MusicPlayActivity extends AppCompatActivity {

    private List<Music> musicList;  //记录音乐列表
    private TextView titleView, singerView, currentTimeView, totalTimeView;// 显示歌曲名、作者信息、当前播放时间、总时间的文本显示框
    private SeekBar playProgress;   // 拖动条
    private Spinner styleSpinner;   //选择播放形式的下拉列表
    private ImageButton control;    // 播放/暂停按钮
    private ImageView picView;      //显示图片
    private ServerReceiver serverReceiver;  // 接收后台服务发送的广播的广播接收器
    private Music currentMusic;     //记录当前播放的音乐
    private boolean isPause = false;  //是否暂停
    private int currentPosition;        //当前音乐的索引
    private int listType;           //音乐列表类型：所有音乐还是播放列表中的音乐
    // 播放模式
    private String[] styles=new String[]{Constants.LIST_LOOP,Constants.SINGLE_LOOP};

    private ObjectAnimator animator;        // 专辑动画控制


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_item);     //加载主界面

        initView();// 初始化界面
        initData();
    }
    //执行初始化操作--显示
    public void initView() {
        Log.d("position","initView");
        styleSpinner=(Spinner)findViewById(R.id.styleSpinner);
        styleSpinner.setAdapter(new ArrayAdapter<String>(this,R.layout.spinner_text,styles));
        styleSpinner.setOnItemSelectedListener(new SpinnerItemClickListener());
        picView=(ImageView)findViewById(R.id.picView);                  // 显示音乐专辑的图片
        currentTimeView = (TextView) findViewById(R.id.currentTime);    // 显示音乐当前播放的时间的文本控件
        totalTimeView = (TextView) findViewById(R.id.totalTime);        // 显示音乐的总时长的文本控件
        titleView = (TextView) findViewById(R.id.title);                // 显示音乐标题的文本控件
        singerView = (TextView) findViewById(R.id.singer);              // 显示音乐演唱者的文本控件
        playProgress = (SeekBar) findViewById(R.id.playProgress);       // 显示当前音乐播放进度的控件
        control = (ImageButton) findViewById(R.id.control);             // 控制播放或暂停的控件
        // 音乐专辑旋转动画
        animator = ObjectAnimator.ofFloat(picView, "rotation", 0f, 360.0f);
        animator.setDuration(10000);        //设置持续时间
        animator.setInterpolator(new LinearInterpolator()); //动画插值
        animator.setRepeatCount(-1);    //设置动画重复次数

        playProgress.setOnSeekBarChangeListener(new MySeekBarChangeListener());// 为拖动条添加事件处理
        //创建广播接收器
        serverReceiver = new ServerReceiver();
        //可以接收到的广播类型，广播过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.COMPLETE_ACTION);    // 音乐播放结束的事件
        filter.addAction(Constants.UPDATE_ACTION);      // 更新进度的动作
        registerReceiver(serverReceiver, filter);           //注册广播接收器

    }

    public void initData() {
        Log.d("position","initData");
        //如果当前音乐为空，从后台服务存储而取
        SharedPreferences musicPreferences = getSharedPreferences("music", Context.MODE_PRIVATE);
        currentPosition = musicPreferences.getInt("position", 0);
        listType = musicPreferences.getInt("listType", Constants.ALL_MUSIC);
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
        currentMusic = musicList.get(currentPosition);//获取当前的音乐
        // 播放模式，默认列表循环
        String styleString=musicPreferences.getString("style", Constants.LIST_LOOP);
        for(int i=0;i<styles.length;i++){
            if(styles[i].equalsIgnoreCase(styleString)){
                styleSpinner.setSelection(i);
                break;
            }
        }
        showInfo();     //显示音乐信息
        isPause=true;   //继续播放
        if (Constants.isPlay == 0) {
            playNewMusic();
        }else {
            animator.start();
        }
        control(null);
    }
    // 显示音乐信息
    public void showInfo(){
        if (Constants.isWEB == 1) {
            // 如果播放的是网络音乐
            totalTimeView.setText(MusicUtils.timeToStringWeb(currentMusic.getTime()));//显示音乐的总时长
            LoadImg.loadWebImg(picView, currentMusic.getPic(), this);
        }else {
            // 播放本地音乐
            totalTimeView.setText(MusicUtils.timeToString(currentMusic.getTime()));//显示音乐的总时长
            Bitmap bitmap = MusicUtils.getAlbumPic(this, currentMusic);
            if(bitmap!=null){
                picView.setImageBitmap(bitmap);
            }else{
                // 否则默认背景图
                picView.setImageResource(R.drawable.background);
            }

        }
        titleView.setText("        " + currentMusic.getName() + "        ");// 显示歌曲名
        singerView.setText(currentMusic.getSinger());   // 显示演唱者
    }
    public void playNewMusic() {
        Log.d("position","playNewMusic");
        currentTimeView.setText(MusicUtils.timeToString(0));//显示当前播放的时间，默认为0
        showInfo();
        playProgress.setProgress(0);                    //进度为0
        control.setImageResource(R.drawable.pause);     //显示暂停的按钮
        // 自定义广播，通知后台播放音乐
        Intent controlIntent = new Intent(Constants.CONTROL_ACTION);
        // 并可以包含一些信息
        controlIntent.putExtra("new", Constants.NEW);       //这是一首新音乐
        controlIntent.putExtra("position",currentPosition);  //传递当前音乐的序号
        controlIntent.putExtra("listType",listType);         //传递音乐列表的类型
        //发送广播
        sendBroadcast(controlIntent);
        isPause=false;//是否暂停为false
        animator.start();
    }
    // 进度条改变监听
    private class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) { // 进度发送变化时调用该方法
        }
        public void onStartTrackingTouch(SeekBar seekBar) {// 开始拖动时调用
        }
        // 结束拖动时调用
        public void onStopTrackingTouch(SeekBar seekBar) {
            //发送广播通知拖动条变化
            Intent seekIntent = new Intent(Constants.SEEKBAR_ACTION);
            //将当前的进度传递进去
            seekIntent.putExtra("progress", seekBar.getProgress());
            sendBroadcast(seekIntent);// 发送广播
        }
    }
    // 广播接收器，用于接收后台服务发送的广播
    private class ServerReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            Log.d("position","ServerReceiver");
            //更新进度的广播处理
            if(intent.getAction() == Constants.UPDATE_ACTION){
                int position = intent.getIntExtra("position",0);              //获取音乐播放的位置
                currentTimeView.setText(MusicUtils.timeToString(position));  //显示当前的播放时长
                //根据位置计算进度条的进度
                playProgress.setProgress((int)(position*1.0/currentMusic.getTime()*100));
            }else if(intent.getAction() == Constants.COMPLETE_ACTION){
                //音乐播放完成的事件处理
                currentPosition = intent.getIntExtra("position", 0);  //获取当前播放的音乐的序号
                currentMusic = musicList.get(currentPosition);          //获取当前的音乐
                showInfo();//显示当前音乐的信息
            }
        }
    }
    // 播放模式选择
    private class SpinnerItemClickListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            Intent styleIntent=new Intent(Constants.UPDATE_STYLE);
            styleIntent.putExtra("style",styles[position]);
            sendBroadcast(styleIntent);
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    // 第一首按钮的事件处理
    public void first(View view) {
        currentPosition=0;
        currentMusic=musicList.get(currentPosition);
        playNewMusic();
    }
    // 前一首按钮的事件处理
    public void pre(View view) {
        currentPosition=(currentPosition-1+musicList.size())%musicList.size();
        currentMusic=musicList.get(currentPosition);
        playNewMusic();
    }
    // 播放和暂停按钮的事件处理
    public void control(View view) {
        Intent controlIntent = new Intent(Constants.CONTROL_ACTION);//控制音乐播放和暂停
        if(!isPause){//如果处于播放状态，发送广播通知暂停
            controlIntent.putExtra("control",Constants.PAUSE);
            Constants.isPlay = 0;
            control.setImageResource(R.drawable.play);//改变图标
            animator.pause();
        }else{//如果处于暂停状态，发送广播通知播放
            controlIntent.putExtra("control",Constants.PLAY);
            Constants.isPlay = 1;
            control.setImageResource(R.drawable.pause);//改变图标
            animator.resume();
        }
        isPause=!isPause;
        sendBroadcast(controlIntent);//发送广播
    }
    // 下一首按钮的事件处理
    public void next(View view) {
        currentPosition=(currentPosition+1) % musicList.size();
        currentMusic=musicList.get(currentPosition);
        playNewMusic();
    }
    // 最后一首按钮的事件处理
    public void last(View view) {
        currentPosition=musicList.size()-1;
        currentMusic=musicList.get(currentPosition);
        playNewMusic();
    }
    //服务销毁时，取消广播接收器的注册
    protected void onDestroy() {
        if (serverReceiver != null) {
            unregisterReceiver(serverReceiver);// Activity销毁时，取消注册
        }
        super.onDestroy();
    }
}
