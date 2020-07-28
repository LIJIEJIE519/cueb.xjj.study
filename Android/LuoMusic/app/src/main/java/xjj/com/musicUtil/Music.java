package xjj.com.musicUtil;

import java.io.Serializable;

/**
 * Created by XJJ on 2018/11/26.
 * 歌曲类
 */

public class Music implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;         //网络音乐
    private String name;       //歌曲文件标题，包括网络
    private String singer;      //歌曲演唱者，包括网络
    private String album;       //歌曲专辑
    private int album_id;       //专辑编号
    private String url;         //歌曲文件路径，包括网络
    private long size;          //歌曲文件大小
    private int time;           //歌曲文件时长，单位为毫秒，包括网络
    private String nameLM;        //歌曲文件名，包含后缀
    private String pic;         //网络音乐
    private String lrc;         //网络音乐


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getLrc() {
        return lrc;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getAlbum() {
        return album;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public String getUrl() {
        return url;
    }

    public long getSize() {
        return size;
    }

    public int getTime() {
        return time;
    }

    public String getNameLM() {
        return nameLM;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setNameLM(String name) {
        this.nameLM = name;
    }

    @Override
    public String toString() {              //显示歌曲名及演唱者
        return "Music [name="+ name +", singer="+ singer +"]";
    }
}
