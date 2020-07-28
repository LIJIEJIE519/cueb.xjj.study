package xjj.com.luomusic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xjj.com.WebUtil.HttpUtil;
import xjj.com.WebUtil.LoadImg;
import xjj.com.listView.Item;
import xjj.com.listView.ItemAdapter;
import xjj.com.musicUtil.Constants;

/*
* 我的界面
* */
public class MineFragment extends Fragment {

    private View view;
    private ImageView bing_img;         // 图片控件
    private ImageView mine_logo;        // logo
    private SwipeRefreshLayout refresh;
    private ListView mine_listView;     // 选项list
    private List<Item> itemList = new ArrayList<>();    // 存储每项选项
    private String[] listItem = {"我的喜欢","最近播放","扫描本地","下载的音乐","设置"};
    //private String url = "https://api.dujin.org/pic/";  //网络图片地址
    private String url = "http://lorempixel.com/600/400/";  //网络图片地址


    public MineFragment() {    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine,null);
            initView();
            initData();
            LoadImg.loadWebImg(bing_img, url, getActivity());
            //loadBingPic();
            ItemAdapter adapter = new ItemAdapter(getContext(), R.layout.mine_item, itemList);
            mine_listView.setAdapter(adapter);

            myListener();

        }
        return view;
    }
    // 初始化界面
    public void initView() {
        bing_img = view.findViewById(R.id.bing_pic_img);
        mine_listView = view.findViewById(R.id.mine_listView);
        mine_logo = view.findViewById(R.id.mine_logo);
        refresh = view.findViewById(R.id.swipe_refresh);

        // 设置对应Item
        Item likes = new Item(listItem[0], R.drawable.collection_no);
        itemList.add(likes);
        Item recentPlay = new Item(listItem[1], R.drawable.recent_play);
        itemList.add(recentPlay);
        Item scanLocal = new Item(listItem[2], R.drawable.scan_local);
        itemList.add(scanLocal);
        Item download = new Item(listItem[3], R.drawable.download);
        itemList.add(download);
        Item set = new Item(listItem[4], R.drawable.setting);
        itemList.add(set);
    }
    // 初始化数据
    public void initData() {
        // 设置下拉刷新颜色
        refresh.setColorSchemeResources(R.color.colorPrimary);
    }
    // 加载必应图片
    public void loadBingPic(){
        // 请求图片
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 获取网络图片
                byte[] bingPic = response.body().bytes();
                final Bitmap bmp = BitmapFactory.decodeByteArray(bingPic, 0, bingPic.length);
                // 将当前线程切换到主线程绘制UI
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bing_img.setImageBitmap(bmp);
                    }
                });
            }
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
    // 点击监听
    public void myListener() {
        // listView每项监听
        mine_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    // 我的喜欢
                    case 0:
                        int favoriteSize = Constants.favoriteList.size();
                        if (favoriteSize > 0) {
                            Toast.makeText(getContext(), "您当前喜欢音乐 "+favoriteSize+" 首.",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(), "您暂未添加喜欢音乐 " ,Toast.LENGTH_LONG).show();
                        }

                        break;
                    // 最近播放
                    case 1:
                        int playSize = Constants.playlist.size();
                        if (playSize > 0) {
                            Toast.makeText(getContext(), "您当前听音乐 "+playSize+" 首.",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(), "您暂未听音乐 " ,Toast.LENGTH_LONG).show();
                        }
                        break;
                    // 启动扫描界面
                    case 2:
                        Intent intent = new Intent(getContext(), ScanActivity.class);
                        startActivity(intent);
                        break;
                    // 下载的音乐
                    case 3:
                        Toast.makeText(getContext(),"正在申请版权哦!",Toast.LENGTH_LONG).show();
                        break;
                    // 设置
                    case 4:
                        Toast.makeText(getContext(),"暂无设置呀!",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        //下拉刷新监听
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 加载图片
                loadBingPic();
                // 结束刷新事件，并隐藏刷新进度条
                refresh.setRefreshing(false);
            }
        });
    }
}
