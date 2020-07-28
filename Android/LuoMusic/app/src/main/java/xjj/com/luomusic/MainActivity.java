package xjj.com.luomusic;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xjj.com.musicUtil.Constants;
import xjj.com.musicUtil.Music;
import xjj.com.musicUtil.MusicService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager viewPager;            // 多页面
    private FragmentPagerAdapter adapter;  // 适配器
    private List<Fragment> fragments;       // 装载底部导航栏

    private LinearLayout mTabPlayList;      // 音乐列表
    private LinearLayout mTabMusic;         // 主音乐界面
    private LinearLayout mTabMine;          // 关于我的

    private Button mImgPlayList;
    private Button mImgMusic;
    private Button mImgMine;

    private SQLiteDatabase mDatabase;        //数据库类--保存我的列表及我的喜欢

    private NavigationView navigationView;  //侧边栏
    private View headerLayout;               //侧边栏的头部栏
    private TextView userNameNav;            // 昵称

    private FloatingActionButton fab;       // 悬浮小按钮
    private Intent intentService;             // 启动服务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 悬浮小按钮
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 启动播放界面活动
                Intent intent = new Intent(MainActivity.this, MusicPlayActivity.class);
                startActivity(intent);
            }
        });
        // 侧边栏
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initViews();    // 初始化视图
        initEvent();    // 初始化事件
        initDatas();
        // 创建Intent，启动音乐服务
        intentService = new Intent(this, MusicService.class);
        startService(intentService);
    }

    // 初始化控件
    public void initViews() {
        viewPager = (ViewPager)findViewById(R.id.id_viewpager);
        // 获得底部导航
        mTabPlayList = (LinearLayout)findViewById(R.id.tab_playList);
        mTabMusic = (LinearLayout)findViewById(R.id.tab_mainMusic);
        mTabMine = (LinearLayout)findViewById(R.id.tab_mine);

        mImgPlayList = (Button)findViewById(R.id.tab_list_img);
        mImgMusic = (Button) findViewById(R.id.tab_mian_img);
        mImgMine = (Button)findViewById(R.id.tab_mine_img);

        // 侧边栏的头部栏--NavigationView是一个RecyclerView
        headerLayout = navigationView.getHeaderView(0);
        // 昵称
        userNameNav = headerLayout.findViewById(R.id.navH_textView);


    }
    // 初始化事件
    public void initEvent() {
        mTabPlayList.setOnClickListener(new myListener());
        mTabMusic.setOnClickListener(new myListener());
        mTabMine.setOnClickListener(new myListener());
    }
    // fragment初始化
    public void initDatas() {
        fragments = new ArrayList<>();
        // 将三个fragment加入
        fragments.add(new MusicListFragment());  // 播放列表
        fragments.add(new MainFragment());     // 主界面
        fragments.add(new MineFragment());      // 我的

        //初始化适配器
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);    // 设置默认为主音乐界面
        //设置ViewPager的切换监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            //页面滚动事件
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            //页面选中事件
            @Override
            public void onPageSelected(int position) {
                //设置position对应的集合中的Fragment
                viewPager.setCurrentItem(position);
                resetImgs();        // 重置图片
                selected(position); // 选择对应项
            }
            @Override
            //页面滚动状态改变事件
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    // 切换图片颜色
    public void selected(int idx) {
        switch (idx) {
            // 选中音乐列表
            case 0:
                mImgPlayList.setBackgroundResource(R.drawable.tab_playlist_press);
                break;
            case 1:
                mImgMusic.setBackgroundResource(R.drawable.tab_main_press);
                break;
            case 2:
                mImgMine.setBackgroundResource(R.drawable.tab_mine_press);
                break;
        }
        viewPager.setCurrentItem(idx);
    }
    // 重置
    public void resetImgs() {
        mImgPlayList.setBackgroundResource(R.drawable.tab_nplaylist);
        mImgMusic.setBackgroundResource(R.drawable.tab_nmain);
        mImgMine.setBackgroundResource(R.drawable.tab_nmine);
    }
    class myListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            //将全部还原
            resetImgs();
            switch (view.getId()) {
                case R.id.tab_playList:
                    selected(0);
                    break;
                case R.id.tab_mainMusic:
                    selected(1);
                    break;
                case R.id.tab_mine:
                    selected(2);
                    break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    // 返回键不退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    // toolbar监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
        }
        return super.onOptionsItemSelected(item);
    }
    // 侧边导航栏
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_message) {
            Toast.makeText(this, "暂无消息！",Toast.LENGTH_LONG).show();
        } else if (id == R.id.item_scan) {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivity(intent);

        } else if (id == R.id.item_change_color) {
            // 一键换肤
            AppBarLayout layout = (AppBarLayout)findViewById(R.id.appBar);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            LinearLayout bottom = (LinearLayout)findViewById(R.id.bottom);
            switch (Constants.whatColor) {
                case 2:
                    layout.setBackgroundResource(R.color.colorAll02);
                    toolbar.setBackgroundResource(R.color.colorAll02);
                    fab.setImageResource(R.drawable.music_float02);
                    bottom.setBackgroundResource(R.color.colorAll02);
                    Constants.whatColor = 3;
                    break;
                case 3:
                    layout.setBackgroundResource(R.color.colorAll03);
                    toolbar.setBackgroundResource(R.color.colorAll03);
                    fab.setImageResource(R.drawable.music_float03);
                    bottom.setBackgroundResource(R.color.colorAll03);
                    Constants.whatColor = 4;
                    break;
                case 4:
                    layout.setBackgroundResource(R.color.colorAll04);
                    toolbar.setBackgroundResource(R.color.colorAll04);
                    fab.setImageResource(R.drawable.music_float04);
                    bottom.setBackgroundResource(R.color.colorAll04);
                    Constants.whatColor = 1;
                    break;
                default:
                    layout.setBackgroundResource(R.color.colorPrimary);
                    toolbar.setBackgroundResource(R.color.colorPrimary);
                    fab.setImageResource(R.drawable.music_float);
                    bottom.setBackgroundResource(R.color.transparent);
                    Constants.whatColor = 2;
                    break;
            }
        } else if (id == R.id.item_update) {
            Toast.makeText(this, "当前为最新版本！",Toast.LENGTH_SHORT).show();

        } else if (id == R.id.item_login) {
            // 登录界面
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.item_exit) {
            stopService(intentService);     //停止服务
            this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // 管理登录信息
    @Override
    protected void onStart() {
        // 设置登录信息
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        String userName = pref.getString("nickName","！请登录");
        if (!userName.equals("！请登录")){
            MenuItem item = navigationView.getMenu().findItem(R.id.item_login);
            item.setTitle("登出");
            userNameNav.setText(userName);
        }
        super.onStart();
    }

    //关闭的时候将我的喜欢中的数据保存到数据库中
    protected void onDestroy() {
        mDatabase.execSQL("delete from music_tb");//删除已有的所有数据
        //循环遍历我的喜欢中的音乐
        for(int i=0;i<Constants.favoriteList.size();i++){
            Music music=Constants.favoriteList.get(i);//获取音乐
            mDatabase.execSQL("insert into music_tb (title,artist,album,album_id,time,url)values(?,?,?,?,?,?)",new String[]{
                    music.getName(),music.getSinger(),music.getAlbum(),music.getAlbum_id()+"",music.getTime()+"",music.getUrl()});//将音乐信息保存到数据库
        }
        super.onDestroy();
    }
}
