package xjj.com.musicUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by XJJ on 2018/11/26.
 * 数据库辅助类
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public String createTableSQL="create table if not exists music_tb" +
            "(_id integer primary key autoincrement,title,artist,album,album_id,time,url)";
    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }
    //数据库创建后，回调该方法，执行建表操作和插入初始化数据
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSQL);
        Log.d("DataCreate", "创建成功");
        //Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
    }
    //数据库版本更新时，回调该方法
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("版本变化:"+oldVersion+"-------->"+newVersion);
    }
}