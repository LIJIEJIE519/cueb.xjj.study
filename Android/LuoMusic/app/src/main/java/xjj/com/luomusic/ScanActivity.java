package xjj.com.luomusic;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.util.List;

import xjj.com.musicUtil.Constants;
import xjj.com.musicUtil.Music;
import xjj.com.musicUtil.MusicUtils;
/*
* 扫描音乐活动
* */
public class ScanActivity extends AppCompatActivity {

    private Button scanBtn;
    private ProgressBar progressBar;
    private List<Music> musicList;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scanBtn = (Button)findViewById(R.id.scan_music);        // 扫描音乐按钮
        progressBar = (ProgressBar)findViewById(R.id.scan_progress);    // 进度条
        // 因为扫描音乐太快，故延时演示进度条扫描
        handler = new Handler();

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                // 延时2.5s显示扫描完成
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            musicList = MusicUtils.getMusicData(getBaseContext());
                            if (musicList.size() > 0) {
                                progressBar.setVisibility(View.GONE);
                                scanBtn.setText("扫描到音乐 : " + musicList.size());
                                scanBtn.setVisibility(View.VISIBLE);
                                Constants.musicList = musicList;
                            }else {
                                scanBtn.setText("未扫描到音乐！");
                                scanBtn.setVisibility(View.VISIBLE);
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },2500);
            }
        });
    }
}
