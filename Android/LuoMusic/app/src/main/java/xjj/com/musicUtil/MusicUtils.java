package xjj.com.musicUtil;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJJ on 2018/11/26.
 * 歌曲辅助类--获取音乐并存入数据库
 */

public class MusicUtils {

    // 获取音乐内容解析器
    public static List<Music> getMusicData(Context context) {
        ContentResolver mResolver = context.getContentResolver();
        if (mResolver != null){     // 获取所有音乐
            //  第一个参数表示系统中音乐提供者的URI, 第五个参数表示查询结果的排序方式。
            Cursor cursor = mResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
                    null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            return cursorToList(cursor, context);
        }
        return null;
    }

    // 获取音乐列表
    public static List<Music> cursorToList(Cursor cursor, Context context) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }
        List<Music>musicList = new ArrayList<>();   // 存放音乐
        while (cursor.moveToNext()){
            Music m = new Music();
            // 获取音乐标题
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            // 获取音乐艺术家
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            if ("<unknown>".equals(artist)){
                artist = "未知";
            }
            // 获取音乐专辑
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            // 音乐专辑图片Id
            int album_id = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
            // 获取音乐大小
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            // 获取音乐持续时间，单位为毫秒
            int time = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            // 获取音乐保存路径
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            // 获取音乐名包含后缀
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String sub = name.substring(name.lastIndexOf(".") + 1); // 从.后开始到末尾截取格式
            if (sub.equals("mp3") && time > 50000) {// 以MP3结尾并且长度大于5秒
                m.setName(title);      // 歌曲标题
                m.setSinger(artist);    // 歌曲的演唱者
                m.setAlbum(album);      // 歌曲所属专辑
                m.setAlbum_id(album_id);// 歌曲所属专辑的编号
                m.setSize(size);        // 歌曲的大小
                m.setTime(time);        // 歌曲的时长
                m.setUrl(url);          // 歌曲存放的路径
                m.setNameLM(name);        // 歌曲名，包含后缀
                musicList.add(m);       // 将歌曲添加到集合中
            }
        }
        cursor.close();
        return musicList;
    }
    // 时间格式转换，将毫秒转换成分秒的形式
    public static String timeToString(int time) {
        int temp = time / 1000;         // 将毫秒转换成秒
        int minute = temp / 60;         // 计算一共有多少分
        int second = temp % 60;         // 除了这些分后，还剩多少秒
        return String.format("%02d:%02d", minute, second);// 以分秒的形式显示
    }
    public static String timeToStringWeb(int time) {
        int minute = time / 60;         // 计算一共有多少分
        int second = time % 60;         // 除了这些分后，还剩多少秒
        return String.format("%02d:%02d", minute, second);// 以分秒的形式显示
    }
    // 从数据库获取音乐
    public static List<Music> getDataFromDB(SQLiteDatabase db) {
        List<Music> musics = new ArrayList<Music>();
        Cursor cursor = db.rawQuery("select * from music_tb", null);
        if (cursor == null || cursor.getCount() == 0) {
            return musics;
        }
        while (cursor.moveToNext()) {
            Music music = new Music();
            music.setName(cursor.getString(cursor.getColumnIndex(("title"))));
            music.setSinger(cursor.getString(cursor.getColumnIndex("artist")));
            music.setAlbum(cursor.getString(cursor.getColumnIndex("album")));
            music.setAlbum_id(cursor.getInt(cursor.getColumnIndex("album_id")));
            music.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            music.setTime(cursor.getInt(cursor.getColumnIndex("time")));
            musics.add(music);
        }
        return musics;
    }

    // 根据音乐获取专辑图片
    public static Bitmap getAlbumPic(Context context, Music music) {
        // 获取内容解析器
        ContentResolver mResolver = context.getContentResolver();
        // 将contentUri和id连接成一个新的Uri
        Uri uri = ContentUris.withAppendedId(Constants.ALBUM_URL, music.getAlbum_id());
        try {
            InputStream inputStream = mResolver.openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException ex) {    // 如果不存在则抛出异常
            try {
                // 获取只读权限
                ParcelFileDescriptor pfd = mResolver.openFileDescriptor(uri, "r");
                if (pfd != null) {
                    FileDescriptor fd = pfd.getFileDescriptor();
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fd);
                    return bitmap;
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        }
    }

}
