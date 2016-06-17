package xiaoliang.lnote.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.NotificationCompat;

import xiaoliang.lnote.R;

/**
 * Created by Administrator on 2016/6/17.
 * 状态栏消息创建工具
 */

public class NotificationUtil {

    private static NotificationManager notificationManager;

    public static void updateProgressNotification(Context context,String name,String msg,int max, int progress, boolean indeterminate,PendingIntent pendingIntent,int tag){
        Notification notification = createProgressNotification(context,name,msg,max,progress,indeterminate,pendingIntent);
        getManager(context).notify(tag,notification);
    }

    public static Notification createProgressNotification(Context context,String name,String msg,int max, int progress, boolean indeterminate,PendingIntent pendingIntent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(name)
                .setContentText(msg)
                .setSmallIcon(R.mipmap.ic_small)
                .setProgress(max,progress,indeterminate)
                .setOngoing(true)
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setGroup(context.getString(R.string.app_name))
                .setTicker(name+":"+msg);
        return builder.build();
    }

    /**
     * 显示一个消息
     * @param context
     * @param name
     * @param msg
     * @param pendingIntent
     * @param tag
     */
    public static void showMsgNotification(Context context,String name,String msg,Bitmap img,PendingIntent pendingIntent,int tag){
        getManager(context).notify(tag,createMsgNotification(context,name,msg,img,pendingIntent));
    }

    public static Notification createMsgNotification(Context context, String name, String msg, Bitmap img, PendingIntent pendingIntent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(name)
                .setContentText(msg)
                .setSmallIcon(R.mipmap.ic_small)
                .setOngoing(false)
                .setAutoCancel(true)
                .setLargeIcon(img)
                .setGroup(context.getString(R.string.app_name))
                .setContentIntent(pendingIntent)
                .setTicker(name+":"+msg);
        return builder.build();
    }

    /**
     * 获取一个消息管理器
     * 这是一个系统服务
     * @param context
     * @return
     */
    public synchronized static NotificationManager getManager(Context context){
        if(notificationManager==null){
            notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }

}
