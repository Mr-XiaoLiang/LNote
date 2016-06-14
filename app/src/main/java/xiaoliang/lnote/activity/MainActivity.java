package xiaoliang.lnote.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import xiaoliang.lnote.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toHome();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void toHome(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(200);
            }
        }, 2000);//2秒后跳转
    }
}
