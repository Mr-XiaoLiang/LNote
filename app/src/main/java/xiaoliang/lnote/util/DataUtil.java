package xiaoliang.lnote.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2016/6/16.
 * 数据的导入及导出
 */

public class DataUtil {



    //导出数据
    public static boolean output(Context context,ProgressListener pro){
        try{
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            String filePath = database.getPath();
            String outPath = Environment.getExternalStorageDirectory().getPath();
            output(filePath,outPath,pro);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean output(String oldName,String newName,ProgressListener pro){
        try{
            int len = -1;
            int all = 0;
            int acc = 0;
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(new File(oldName)));
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File(newName)));
            all = inputStream.available();
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer))!=-1){
                acc+=len;
                outputStream.write(buffer);
            }
            if(pro!=null){
                pro.progress(all,acc,acc*1.0/all);
            }
            outputStream.close();
            inputStream.close();
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }
    }

    public interface ProgressListener{
        /**
         * 当前进度
         * @param all 总量
         * @param accomplish 已经完成的量
         * @param pro 完成的百分比
         */
        void progress(long all,long accomplish,double pro);
    }

}
