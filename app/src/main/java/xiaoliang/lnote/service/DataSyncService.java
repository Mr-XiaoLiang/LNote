package xiaoliang.lnote.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import xiaoliang.lnote.util.DataUtil;
import xiaoliang.lnote.util.NotificationUtil;

/**
 * 数据同步服务
 */
public class DataSyncService extends Service implements DataUtil.ProgressListener {

    private boolean optputRunning;
    private boolean isOut;
    private int outTag = 464754;

    public DataSyncService() {
        optputRunning = false;
        isOut = true;
    }

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
        isOut = intent.getBooleanExtra("type",true);
        if(isOut){
            if(!optputRunning){
                new Thread(new OutputRunnable(this)).start();
                optputRunning = true;
            }else{
                Toast.makeText(this,"已经开始导出",Toast.LENGTH_SHORT).show();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 与activity连接时执行，多次调用，多次执行
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 线程同步用的handler
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    optputRunning = false;
                    if((boolean)msg.obj)
                        NotificationUtil.showMsgNotification(DataSyncService.this,"数据导出","数据已导出，请查看存储根目录",null,null,outTag);
                    else
                        NotificationUtil.showMsgNotification(DataSyncService.this,"数据导出","数据导出失败,可能是没有数据",null,null,outTag);
                    break;
                case 500:
                    optputRunning = false;
                    Exception e = (Exception) msg.obj;
                    Toast.makeText(DataSyncService.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void outputProgress(int all,int pro){
        boolean indeterminate = false;
        String title,msg;
        if(all==0){
            indeterminate = true;
            title = "数据导出";
            msg = "正在整理数据";
        }else{
            title = "数据导出";
            msg = "正在导出数据";
        }
        NotificationUtil.updateProgressNotification(this,title,msg,all,pro,indeterminate,null,outTag);
    }

    @Override
    public void progress(long all, long accomplish, double pro) {
        outputProgress((int)all,(int)accomplish);
    }

    private class OutputRunnable implements Runnable{

        private Context context;

        public OutputRunnable(Context context) {
            this.context = context;
        }
        @Override
        public void run() {
            Message message = new Message();
            try {
                boolean b = DataUtil.output(context,DataSyncService.this);
                message.what = 200;
                message.obj = b;
            }catch (Exception e){
                message.what = 500;
                message.obj = e;
            }
            handler.sendMessage(message);
        }
    }

}
