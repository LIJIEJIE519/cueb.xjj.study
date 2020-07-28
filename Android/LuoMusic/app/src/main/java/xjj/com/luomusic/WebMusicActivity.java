package xjj.com.luomusic;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xjj.com.WebUtil.HttpUtil;
import xjj.com.musicUtil.Constants;
import xjj.com.musicUtil.Music;
import xjj.com.musicUtil.MusicUtils;


public class WebMusicActivity extends AppCompatActivity {
    private ListView webListView;
    private ProgressBar progressBar;
    private FloatingActionButton fab;
    private TextView load;
    private String keyQuery;    // 查询关键字
    private List<Music> webMusicList = new ArrayList<>();    // 存储网络音乐信息
    //private String url = "https://api.bzqll.com/music/tencent/search?key=579621905&s=烟花易冷&limit=10&offset=0&type=song";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_music);

        webListView = (ListView)findViewById(R.id.webMusicList);
        progressBar = (ProgressBar)findViewById(R.id.webProgress);
        load = (TextView)findViewById(R.id.load);

        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动播放界面活动
                Intent intent = new Intent(WebMusicActivity.this, MusicPlayActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        keyQuery = intent.getStringExtra("keyQuery");   //获得查询关键字
        String url = "https://api.bzqll.com/music/tencent/search?key=579621905&s="+keyQuery+"&limit=10&offset=0&type=song";
        loadWebMusic(url);     // 加载网络音乐信息
        myListener();
    }
    // 加载网络音乐
    public void loadWebMusic(String  url) {
        // 请求网络
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                // 第一步解析
                String jsonData = parseJson(string);
                if (jsonData != null || jsonData.length() != 0) {
                    Gson gson = new Gson();
                    // 第二步解析数组每一首
                    webMusicList = gson.fromJson(jsonData, new TypeToken<List<Music>>(){}.getType());
                    Constants.webMusicList = webMusicList;
                    for (Music webMusic : webMusicList) {
                        Log.d("hhhhh", "url: " + webMusic.getUrl());
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 由于网络请求耗时，该方法只能用在这，且要回到主线程绘制
                            webListView.setAdapter(new WebAdapter());
                            // 隐藏加载框及文字
                            progressBar.setVisibility(View.INVISIBLE);
                            load.setVisibility(View.INVISIBLE);

                        }
                    });
                }else {
                    Toast.makeText(getBaseContext(), "网络请求失败！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    // 第一步请求获得总的数据
    public String parseJson(String json) {
        String result = null;
        String code = null;
        String data = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            result = jsonObject.getString("result");        // SUCCESS
            code = jsonObject.getString("code");            // 200
            data = jsonObject.getString("data");
        }catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    // ViewPage适配器
    private class WebAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return webMusicList.size();
        }
        @Override
        public Object getItem(int i) {
            return webMusicList.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                // 将布局文件转换为View对象
                view = LinearLayout.inflate(getBaseContext(),R.layout.music_item, null);
            }
            ImageView icon = view.findViewById(R.id.icon);       // 显示图标的控件
            TextView title = view.findViewById(R.id.title);       // 显示歌曲名
            TextView artist = view.findViewById(R.id.artist);     // 显示演唱者
            TextView time = view.findViewById(R.id.time);         // 显示时间
            // 获得网络专辑图并设置
            //LoadImg.loadWebImg(icon, webMusicList.get(i).getPic(),WebMusicActivity.this);
            icon.setImageResource(R.drawable.music);
            title.setText(webMusicList.get(i).getName());
            artist.setText(webMusicList.get(i).getSinger());
            time.setText(MusicUtils.timeToStringWeb(webMusicList.get(i).getTime()));

            return view;
        }
    }
    // 音乐项单击事件处理
    public void myListener() {
        webListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // 单击该音乐时发送广播控制音乐播放与暂停
                Intent intent = new Intent(Constants.CONTROL_ACTION);
                intent.putExtra("listType", Constants.WEB_MUSIC);   // 音乐播放类型为所有
                // 判断被点击的音乐是否是新音乐
                if (position != Constants.currentPosition) {
                    // 如果不是上一次点击的音乐，则是新音乐
                    intent.putExtra("new", Constants.NEW);          //这是一首新音乐
                } else {
                    // 不是新音乐，控制播放暂停
                    if (Constants.isPlay == 0) {
                        // 没有播放,则播放
                        intent.putExtra("control", Constants.PLAY);
                        Constants.isPlay = 1;
                    } else {
                        // 否则暂停音乐
                        intent.putExtra("control", Constants.PAUSE);
                        Constants.isPlay = 0;
                    }
                }
                Constants.currentPosition = position;    // 记录被点击的音乐
                Constants.isWEB = 1;
                intent.putExtra("position", position);     // 传送该音乐序号
                // 发送广播音乐控制广播
                sendBroadcast(intent);
            }
        });
    }
}
