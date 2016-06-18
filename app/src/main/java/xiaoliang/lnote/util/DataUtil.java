package xiaoliang.lnote.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/6/16.
 * 数据的导入及导出
 */

public class DataUtil {

    private static ExecutorService cachedThreadPool;

    public static boolean exists(Context context){
        File file=new File(getDataPath(context));
        return file.exists()&&!file .isDirectory();
    }

    public static String getDataPath(Context context){
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        return database.getPath();
    }

    //导出数据
    public static boolean output(Context context, ProgressListener pro){
        try {
            if(!exists(context))
                return false;
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            String filePath = database.getPath();
            String outPath = Environment.getExternalStorageDirectory().getPath();
            output(filePath, outPath, pro);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean output(String oldName, String newName, ProgressListener pro) throws Exception {
        int len = -1;
        int all = 0;
        int acc = 0;
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(oldName)));
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(newName)));
        all = inputStream.available();
        byte[] buffer = new byte[1024];
        while ((len = inputStream.read(buffer)) != -1) {
            acc += len;
            outputStream.write(buffer);
        }
        if (pro != null) {
            all = all > 0 ? all : 0;
            acc = acc > 0 ? acc : 1;
            pro.progress(all, acc, acc * 1.0 / all);
        }
        outputStream.close();
        inputStream.close();
        return true;
    }

    public interface ProgressListener {
        /**
         * 当前进度
         *
         * @param all        总量
         * @param accomplish 已经完成的量
         * @param pro        完成的百分比
         */
        void progress(long all, long accomplish, double pro);
    }

    /**
     * 使用线程池执行任务
     * @param runnable
     */
    public static synchronized void execute(Runnable runnable){
        if(cachedThreadPool==null)
            cachedThreadPool = Executors.newCachedThreadPool();
        cachedThreadPool.execute(runnable);
    }
}
