package xiaoliang.lnote.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import xiaoliang.lnote.bean.AmountStatusBean;

/**
 * Created by LiuJ on 2016/6/16.
 * 数据库帮助类
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "LNote"; //数据库名称
    private static final int version = 1; //数据库版本
    public static final String AMOUNT_STATUS_TABLE = "AMOUNT_STATUS_TABLE";
    public static final String NOTE_TABLE = "NOTE_TABLE";
    private static DatabaseHelper helper;
    private static SQLiteDatabase db;

    private DatabaseHelper(Context context) {
        super(context,DB_NAME,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+AMOUNT_STATUS_TABLE+"(id INTEGER PRIMARY KEY,color int,name varchar);");
        db.execSQL("create table "+NOTE_TABLE+"(id INTEGER PRIMARY KEY,time date,money REAL,note varchar,type int,amount int);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DatabaseHelper Helper(Context context){
        if(helper == null){
            helper = new DatabaseHelper(context);
        }
        return helper;
    }

    public static  synchronized SQLiteDatabase DB(Context context){
        if(db==null){
            db = Helper(context).getWritableDatabase();
        }
        return db;
    }

    /**
     * 查询收支的类型
     * 其实我想写点英文装逼一下的，但是实在写不出来
     * 所以就不写了，写点中文意思一下吧，但是不多写一点
     * 显得不专业，因为源码里面都是写很多的
     * @param context 上下文
     * @return 数据集合
     */
    public static ArrayList<AmountStatusBean> getAmountStatus(Context context){
        SQLiteDatabase sql = DB(context);
        Cursor c = sql.rawQuery("select id,color,name from "+AMOUNT_STATUS_TABLE,new String[]{});
        ArrayList<AmountStatusBean> list = new ArrayList<>();
        AmountStatusBean bean;
        while(c.moveToNext()){
            bean = new AmountStatusBean();
            bean.id = c.getInt(c.getColumnIndex("id"));
            bean.color = c.getInt(c.getColumnIndex("color"));
            bean.name = c.getString(c.getColumnIndex("name"));
            list.add(bean);
        }
        return list;
    }

    /**
     * 添加一条收支类型
     * @param context 上下文
     * @param color 颜色
     * @param name 名字
     * @return 当前插入记录的ID
     */
    public static long addAmountStatus(Context context,int color,String name){
        SQLiteDatabase sql = DB(context);
        ContentValues values = new ContentValues();
        values.put("color",color);
        values.put("name",name);
        values.put("id","null");
        return sql.insert(AMOUNT_STATUS_TABLE,null,values);
    }

    /**
     * 删除一条记录
     * @param context 上下文
     * @param id id就是id，不解释
     * @return the number of rows affected（复制delete方法的）
     */
    public static int delAmountStatus(Context context,int id){
        return DB(context).delete(AMOUNT_STATUS_TABLE,"id = ?",new String[]{String.valueOf(id)});
    }

    /**
     * 更新一条记录
     * @param context 上下文
     * @param id id就是id，不解释
     * @param color 颜色啊
     * @param name 就是个名字而已
     * @return the number of rows affected（复制update方法的）
     */
    public static int updAmountStatus(Context context,int id,int color,String name){
        SQLiteDatabase sql = DB(context);
        ContentValues values = new ContentValues();
        values.put("color",color);
        values.put("name",name);
        return sql.update(AMOUNT_STATUS_TABLE,values,"id = ?",new String[]{String.valueOf(id)});
    }

}
