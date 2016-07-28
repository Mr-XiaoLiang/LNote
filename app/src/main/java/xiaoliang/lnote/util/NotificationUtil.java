package xiaoliang.lnote.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.NotificationCompat;

import xiaoliang.lnote.R;

/**
 * Created by LiuJ on 2016/6/17.
 * 状态栏消息创建工具
 */

public class NotificationUtil {

    private static NotificationManager notificationManager;

    /**
     * 创建并更新进度条消息
     * @param context 上下文
     * @param name 标题
     * @param msg 详细
     * @param max 最大进度值
     * @param progress 当前进度
     * @param indeterminate 是否循环滚动
     * @param pendingIntent 点击跳转意图
     * @param tag 标签（用于多进度消息）
     */
    public static void updateProgressNotification(Context context,String name,String msg,int max, int progress, boolean indeterminate,PendingIntent pendingIntent,int tag){
        Notification notification = createProgressNotification(context,name,msg,max,progress,indeterminate,pendingIntent);
        getManager(context).notify(tag,notification);
    }

    /**
     * 创建一个进度条消息
     * @param context 上下文
     * @param name 标题
     * @param msg 详细
     * @param max 最大进度值
     * @param progress 当前进度
     * @param indeterminate 是否循环滚动
     * @param pendingIntent 点击跳转意图
     * @return 返回消息本身
     */
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
     * @param context 上下文
     * @param name 标题
     * @param msg 消息内容
     * @param pendingIntent 点击跳转意图
     * @param tag 消息更新或清除
     */
    public static void showMsgNotification(Context context,String name,String msg,Bitmap img,PendingIntent pendingIntent,int tag){
        Notification notification = createMsgNotification(context,name,msg,img,pendingIntent);
        notification.defaults = Notification.DEFAULT_ALL;
        getManager(context).notify(tag,notification);
    }

    /**
     * 创建一个普通消息
     * @param context 上下文
     * @param name 标题
     * @param msg 消息内容
     * @param pendingIntent 点击跳转意图
     * @return
     */
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
     * 关闭某一个消息
     * @param context 上下文
     * @param tag 标签
     */
    public static void cancel(Context context,int tag){
        getManager(context).cancel(tag);
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
