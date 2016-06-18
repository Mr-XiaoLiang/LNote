package xiaoliang.lnote.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/6/16.
 * 数据库帮助类
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "LNote"; //数据库名称
    private static final int version = 1; //数据库版本
    public static final String AMOUNT_STATUS_TABLE = "AMOUNT_STATUS_TABLE";
    public static final String NOTE_TABLE = "NOTE_TABLE";

    public DatabaseHelper(Context context) {
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+AMOUNT_STATUS_TABLE+"(hello varchar);");
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
