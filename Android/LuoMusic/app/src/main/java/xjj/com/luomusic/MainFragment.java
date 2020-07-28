package xjj.com.luomusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import xjj.com.musicUtil.Constants;


public class MainFragment extends Fragment {

    private View view = null;      // 适配新界面
    private int currentPosition;    // 当前音乐

    private pl.droidsonroids.gif.GifImageView gif;
    private ImageButton control;
    private ImageButton collect;
    private ImageButton gif_next;

    private IntentFilter filter;       // 广播过滤器
    private MyBDReceiver receiver;     // 广播接收器
    //private int rand = 0;               // 控制随机图

    public MainFragment() {}

    // 加载时即扫描音乐
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 判断Android>5.0才能使用
        if (Build.VERSION.SDK_INT >= 21) {
            // 好的当前活动的DecorView,在改变UI显示
            View decorView = getActivity().getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            // 使其状态栏呈现透明色
            getActivity().getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        super.onCreate(savedInstanceState);
    }

    //布局显示时调用--添加音乐
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_music, null);
            initView();     // 初始化界面
            myListener();   // 按钮监听器
        }
        // 添加广播过滤器
        filter = new IntentFilter();
        receiver = new MyBDReceiver();
        // 创建广播管理器
        //BDManager = LocalBroadcastManager.getInstance(getActivity());
        filter.addAction(Constants.CONTROL_ACTION);  // 监听新音乐广播
        //BDManager.registerReceiver(receiver, filter);   // 注册本地广播--没用
        getActivity().registerReceiver(receiver, filter);

        return view;
    }
    // 初始化界面
    public void initView() {
        gif = view.findViewById(R.id.music_gif);
        control = view.findViewById(R.id.imgBt);
        collect = view.findViewById(R.id.collect);
        gif_next = view.findViewById(R.id.gif_next);
        setRandGif();

    }
    // 按钮监听器
    public void myListener() {
        // gif点击控制播放暂停
        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果当前音乐为空，从后台服务存储而取
                SharedPreferences musicPreferences = getActivity().getSharedPreferences("music", Context.MODE_PRIVATE);
                currentPosition = musicPreferences.getInt("position", 0);
                // 设置广播控制播放
                Intent intent = new Intent(Constants.CONTROL_ACTION);
                if (currentPosition != Constants.currentPosition) {
                    // 如果不是上一次点击的音乐，则是新音乐
                    intent.putExtra("new", Constants.NEW);          //这是一首新音乐
                } else{
                    if (Constants.isPlay == 0) {
                        // 没有播放,则播放并设置播放图片
                        intent.putExtra("control", Constants.PLAY);
                        control.setImageResource(R.drawable.gif_pause);
                        Constants.isPlay = 1;
                    } else {
                        // 否则暂停音乐
                        intent.putExtra("control", Constants.PAUSE);
                        control.setImageResource(R.drawable.gif_play);
                        Constants.isPlay = 0;
                    }
                }
                Constants.currentPosition = currentPosition;    // 记录被点击的音乐
                intent.putExtra("position", currentPosition);     // 传送该音乐序号
                // 发送广播音乐控制广播
                getActivity().sendBroadcast(intent);
                // 是图片出现
                control.setVisibility(View.VISIBLE);
                // 两秒后自动隐藏
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        control.setVisibility(View.INVISIBLE);
                    }
                },2000);
            }
        });
        // 收藏按钮点击监听
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Constants.isFavorite) {
                    // 不是喜欢
                    collect.setImageResource(R.drawable.collection_yes);
                    // 变成喜欢
                    Constants.isFavorite = true;
                    // 将当前音乐添加至我的喜欢
                    Constants.favoriteList.add(Constants.musicList.get(currentPosition));
                }else {
                    // 是喜欢--变成不喜欢
                    collect.setImageResource(R.drawable.collection_no);
                    Constants.isFavorite = false;
                    Constants.favoriteList.remove(Constants.musicList.get(currentPosition));
                }

            }
        });
        // 下一首点击监听
        gif_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 设置随机动画
                setRandGif();
                // 下一首
                currentPosition=(currentPosition+1) % Constants.musicList.size();
                Intent intent = new Intent(Constants.CONTROL_ACTION);
                intent.putExtra("new", Constants.NEW);           //这是一首新音乐
                Constants.currentPosition = currentPosition;    // 记录被点击的音乐
                intent.putExtra("position", currentPosition);     // 传送该音乐序号
                getActivity().sendBroadcast(intent);               // 发送广播音乐控制广播
                // 判断是否我的喜欢
                favorite();
            }
        });
    }
    // 设置随机动画
    public void setRandGif() {
        /*while (true) {
            int tRand = new Random().nextInt(22)+1;
            if (tRand != rand){
                rand = tRand;
                break;
            }
        }*/
        //int id = (rand+1) % 22;
        int rand = new Random().nextInt(22)+1;;
        String gifName = "g"+rand;
        int imgId = getResources().getIdentifier(gifName, "drawable", "xjj.com.luomusic");
        gif.setBackgroundResource(imgId);
    }
    // 广播接收器
    public class MyBDReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == Constants.CONTROL_ACTION){
                if (intent.getIntExtra("new",-1) != -1) {
                    // 如果当前新音乐在我的喜欢列表里
                   favorite();
                }
            }
        }
    }
    // 判断当前播放音乐是否在我的喜欢里
    public void favorite() {
        if (Constants.favoriteList.contains(Constants.musicList.get(Constants.currentPosition))){
            Log.d("favoriteList","yes");
            //已经在我的喜欢列表，收藏图片
            collect.setImageResource(R.drawable.collection_yes);
            Constants.isFavorite = true;
        }else {
            Log.d("favoriteList","no");
            collect.setImageResource(R.drawable.collection_no);
            Constants.isFavorite = false;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销广播
        getActivity().unregisterReceiver(receiver);
    }
}
