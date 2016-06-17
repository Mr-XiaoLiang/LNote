package xiaoliang.lnote.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

import xiaoliang.lnote.util.NotificationUtil;

/**
 * 数据同步服务
 */
public class DataSyncService extends Service {

    private NotificationManager notificationManager;
    private Notification notification;

    public DataSyncService() {}

    //仅仅在创建时执行
    @Override
    public void onCreate() {
        super.onCreate();

    }
    /**
     * 因为onStart已经被弃用，所以使用新的方法
     * 多次调用则多次执行
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }
    /**
     * 与activity连接时执行，多次调用，多次执行
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
        }
    };
    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void outputProgress(int all,int pro){
        boolean indeterminate = false;
        if(all==0){
            indeterminate = true;
        }
        NotificationUtil.updateProgressNotification(this,"数据正在导出","",all,pro,indeterminate,null,3562);
    }

}
