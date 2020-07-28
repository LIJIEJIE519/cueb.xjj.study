package xjj.com.luomusic;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import xjj.com.musicUtil.Constants;
import xjj.com.musicUtil.Music;
import xjj.com.musicUtil.MusicUtils;
import xjj.com.musicUtil.MyDataBaseHelper;

/*
* 启动页
* */
public class StartActivity extends Activity {

    private List<Music> musicList;
    private MyDataBaseHelper mHelper;       //数据库辅助类
    private SQLiteDatabase mDatabase;        //数据库类
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start);
        // 从系统获取音乐
        musicList = MusicUtils.getMusicData(this);
        // 赋值常量类音乐列表
        Constants.musicList = musicList;
        mHelper = new MyDataBaseHelper(this, "music", null, 1);   //得到数据库辅助类
        mDatabase = mHelper.getWritableDatabase();                  //获取数据库
        Constants.favoriteList = MusicUtils.getDataFromDB(mDatabase);   //获取我的喜欢
        // 此处音乐已经扫描完成--暂未提供播放列表
        //Constants.playlist = MusicUtils.getDataFromDB(mDatabase);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(intent);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },2500);
    }
}
