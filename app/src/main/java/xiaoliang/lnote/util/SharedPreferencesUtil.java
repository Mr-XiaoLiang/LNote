package xiaoliang.lnote.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import xiaoliang.lnote.R;

/**
 * Created by LiuJ on 2016/6/14.
 * 数据存储类
 */
public class SharedPreferencesUtil {

    private static final String SHARED_PREFERENCES_NAME = "LNote";
    private static final String THEME_NAME = "THEME";
    private static final String DOUBLE_EXIT = "DOUBLE_EXIT";
    private static final String SYNCHRONIZED_TO_CALENDAR = "SYNCHRONIZED_TO_CALENDAR";
    private static final String REVERSE_ORDER = "REVERSE_ORDER";

    public static <T> boolean put(Context context, String name, T value) {
        try {
            SharedPreferences mySharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            if (value instanceof String)
                editor.putString(name, (String) value);
            if (value instanceof Boolean)
                editor.putBoolean(name, (Boolean) value);
            if (value instanceof Float)
                editor.putFloat(name, (Float) value);
            if (value instanceof Integer)
                editor.putInt(name, (Integer) value);
            if (value instanceof Long)
                editor.putLong(name, (Long) value);
            if (value instanceof Set)
                editor.putStringSet(name, (Set) value);
            editor.commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static <T> Object get(Context context,String name,T defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,Context.MODE_PRIVATE);
        Object value = null;
        if (defValue instanceof String)
            value = sharedPreferences.getString(name,(String)defValue);
        if (defValue instanceof Boolean)
            value = sharedPreferences.getBoolean(name,(Boolean)defValue);
        if (defValue instanceof Float)
            value = sharedPreferences.getFloat(name,(Float) defValue);
        if (defValue instanceof Integer)
            value = sharedPreferences.getInt(name,(Integer) defValue);
        if (defValue instanceof Long)
            value = sharedPreferences.getLong(name,(Long)defValue);
        if (defValue instanceof Set)
            value = sharedPreferences.getStringSet(name,(Set)defValue);
        return value;
    }

    public static int getTheme(Context context){
        return (Integer)get(context,THEME_NAME, R.style.AppTheme);
    }

    public static boolean setTheme(Context context,int theme){
        return put(context,THEME_NAME,theme);
    }

    public static boolean setDoubleExit(Context context,boolean b){
        return put(context,DOUBLE_EXIT,b);
    }

    public static boolean getDoubleExit(Context context){
        return (boolean)get(context,DOUBLE_EXIT,true);
    }

    public static boolean setSynchronizedToCalendar(Context context,boolean b){
        return put(context,SYNCHRONIZED_TO_CALENDAR,b);
    }

    public static boolean getSynchronizedToCalendar(Context context){
        return (boolean)get(context,SYNCHRONIZED_TO_CALENDAR,true);
    }

    public static boolean setReverseOrder(Context context,boolean b){
        return put(context,REVERSE_ORDER,b);
    }

    public static boolean getReverseOrder(Context context){
        return (boolean)get(context,REVERSE_ORDER,true);
    }

}
