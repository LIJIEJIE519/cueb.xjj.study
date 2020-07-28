package xjj.com.musicUtil;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJJ on 2018/11/26.
 * 常量类
 */
public class Constants {
    public static List<Music> musicList = new ArrayList<Music>();         //所有音乐集合
    public static List<Music> playlist = new ArrayList<Music>();          //音乐播放列表
    public static List<Music> favoriteList = new ArrayList<Music>();      //我的喜欢列表
    public static final String CONTROL_ACTION="com.xjj.android.control";  //控制音乐播放动作，播放或暂停
    public static final String SEEKBAR_ACTION="com.xjj.android.seekbar";  //音乐进度发送变化动作
    public static final String COMPLETE_ACTION="com.xjj.android.complete";//音乐播放结束动作
    public static final String UPDATE_ACTION="com.xjj.android.update";     //更新进度条
    public static final String UPDATE_STYLE="com.xjj.android.style";       //更新播放形式
    public static final Uri ALBUM_URL= Uri.parse("content://media/external/audio/albumart");// 专辑路径
    public static final String LIST_LOOP="列表循环";
    public static final String SINGLE_LOOP="单曲循环";
    public static final int NEW=6;              //开始一首新的音乐
    public static final int PLAY=1;             //播放
    public static final int PAUSE=2;            //暂停
    public static final int ALL_MUSIC=1;    //播放所有的音乐
    public static final int PLAY_MUSIC=2;    //播放列表的音乐
    public static final int WEB_MUSIC = 3;    //播放网络的音乐
    public static int isPlay = 0;               //判断是否在播放音乐，1为播放，0默认未播放
    public static int currentPosition = 0;     //保存当前正在播放音乐的序号
    public static int whatColor = 2;            //保存当前颜色
    public static boolean isFavorite = false;       //控制我的喜欢

    public static List<Music> webMusicList = new ArrayList<>();      // 网络音乐集合
    public static int isWEB = 0;    //播放网络的音乐

    public static String WebMusic = null;
}
