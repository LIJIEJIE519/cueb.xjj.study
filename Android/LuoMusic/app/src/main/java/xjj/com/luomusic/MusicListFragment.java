package xjj.com.luomusic;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import xjj.com.musicUtil.LocalMusicList;

/*
* 音乐列表
* */
public class MusicListFragment extends Fragment implements ViewPager.OnPageChangeListener {

    private View view = null;                   // 适配新界面
    private RadioGroup radioGroup = null;       // radioGroup为了让radioButton单选
    private ViewPager viewPager = null;         // 装页面
    private HorizontalScrollView hScrollView = null;
    private Toolbar toolbar;
    //设置ViewPager的fragment
    private List<Fragment> newChannelList=new ArrayList<>();
    private PageFragmentAdapter adapter;

    public MusicListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /*
     * 初始化歌曲列表界面
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            // 获得其页面排版转换为对象
            view = inflater.inflate(R.layout.fragment_musiclist, null);

            radioGroup = view.findViewById(R.id.rgChannel);
            hScrollView = view.findViewById(R.id.hvChannel);
            viewPager = view.findViewById(R.id.musicListVP);
            toolbar = view.findViewById(R.id.toolbar);
            //设置切换项监听
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    viewPager.setCurrentItem(i);
                }
            });
            // 初始化里面内容
            initViewPager();
            // 初始化标签
            //initTab(inflater);
            ;
        }
        return view;
    }
    public void initViewPager() {

        // 添加本地音乐列表
        LocalMusicList musicListFragment = new LocalMusicList();
        newChannelList.add(musicListFragment);

        //定义ViewPager的adapter
        adapter=new PageFragmentAdapter(super.getActivity().getSupportFragmentManager(), newChannelList);
        //使用定义的adapter将ViewPager与频道fragment列表关联起来
        viewPager.setAdapter(adapter);
        //设置缓存2个fragment页面
        viewPager.setOffscreenPageLimit(1);
        //默认情况下在ViewPager中显示fragment频道列表中第一个fragment
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }
    // fragment 里加toolbar必须加如下方法
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // toolbar出现
        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        toolbar= (Toolbar) appCompatActivity.findViewById(R.id.toolbar);
        appCompatActivity.setSupportActionBar(toolbar);


        // 监听左边侧边栏打开
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        /*if (position == 0) {
            toolbar.setName("本地音乐");
        }else if (position == 1) {
            toolbar.setName("网络音乐");
        }*/
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
    // toolBar搜索框
    @Override
    public void onCreateOptionsMenu(Menu menu, final MenuInflater inflater) {

        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchableInfo info = searchManager.getSearchableInfo(getActivity().getComponentName());
        searchView.setSearchableInfo(info);
        searchView.setQueryHint("搜索网络音乐");
        searchView.setIconifiedByDefault(true);     // 显示
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 键盘搜索时调用
                Intent intent = new Intent(getActivity(), WebMusicActivity.class);
                intent.putExtra("keyQuery",query);  // 传入查询关键字
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

/*    @Override
    public void onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.localMusic:break;
        }
        super.onOptionsItemSelected(item);
    }*/

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.localMusic:
                Log.d("item", "hhhhh");
                break;
            default:
                Log.d("item", "hhhhh");
        }
        return super.onContextItemSelected(item);
    }
}
