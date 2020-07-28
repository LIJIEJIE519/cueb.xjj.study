package xjj.com.musicUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import xjj.com.luomusic.R;

/**
 * Created by XJJ on 2018/11/27.
 * 音乐列表显示类
 */

public class LocalMusicList extends ListFragment {
    public List<Music> musicList;

    @Override
    public void onCreate(Bundle savedInstanceState) {      // 创建类时调用
        super.onCreate(savedInstanceState);
    }

    //布局显示时调用--添加音乐
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        musicList = Constants.musicList;     // 赋值常量类音乐列表
        if (musicList != null) {
            // 如果音乐不为空，装入适配器
            setListAdapter(new MusicAdapter());
        } else {
            Toast.makeText(this.getActivity(), "请添加音乐...", Toast.LENGTH_SHORT).show();
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        // 为音乐列表注册上下文菜单
        registerForContextMenu(getListView());
        super.onStart();
    }

    // 适配器类
    private class MusicAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            // 返回歌曲数量
            return musicList.size();
        }
        @Override
        public Object getItem(int i) {
            return musicList.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        // 页面构造方法
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                // 将布局文件转换为View对象
                view = LinearLayout.inflate(getActivity(), R.layout.music_item, null);
            }
            ImageView icon = view.findViewById(R.id.icon);       // 显示图标的控件
            TextView title = view.findViewById(R.id.title);       // 显示歌曲名
            TextView artist = view.findViewById(R.id.artist);     // 显示演唱者
            TextView time = view.findViewById(R.id.time);         // 显示时间
            Bitmap bitmap = MusicUtils.getAlbumPic(getActivity(), musicList.get(i));    // 显示专辑图片
            if (bitmap != null) {
                icon.setImageBitmap(bitmap);
            } else {
                // 没有则显示默认图片
                icon.setImageResource(R.drawable.music);
            }
            title.setText(musicList.get(i).getName());
            artist.setText(musicList.get(i).getSinger());
            time.setText(MusicUtils.timeToString(musicList.get(i).getTime()));

            return view;
        }
    }
    // 音乐项单击事件处理
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // 单击该音乐时发送广播控制音乐播放与暂停
        Intent intent = new Intent(Constants.CONTROL_ACTION);
        intent.putExtra("listType", Constants.ALL_MUSIC);   // 音乐播放类型为所有
        Constants.isWEB = 0;
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
        intent.putExtra("position", position);     // 传送该音乐序号
        // 发送广播音乐控制广播
        getActivity().sendBroadcast(intent);
    }
}