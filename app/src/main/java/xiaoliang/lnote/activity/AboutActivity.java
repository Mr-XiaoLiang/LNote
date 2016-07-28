package xiaoliang.lnote.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import xiaoliang.lnote.R;
import xiaoliang.lnote.util.SharedPreferencesUtil;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private Button button;
    private ClipData myClip;
    private ClipboardManager myClipboard;
    private static final String GitPath = "https://github.com/Mr-XiaoLiang/LNote";
    private View img, copyright;
    private int imgInt = 0, copyrightInt = 0;
    private int allInt = 0;
    private boolean firstOpen = false;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_about);
        setSupportActionBar(toolbar);
        backEnable(true);
        button = (Button) findViewById(R.id.about_copy);
        img = findViewById(R.id.about_img);
        copyright = findViewById(R.id.about_version);
        button.setOnClickListener(this);
        img.setOnClickListener(this);
        copyright.setOnClickListener(this);
        myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        allInt = (int)SharedPreferencesUtil.get(this,"ALLINT",0);
        firstOpen = (boolean)SharedPreferencesUtil.get(this,"FIRSTOPEN",true);
        version = getVersion();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_copy:
                copy();
                break;
            case R.id.about_img:
                onImgClick();
                break;
            case R.id.about_version:
                onVerClick();
                break;
        }
    }

    private void onVerClick(){
        copyrightInt++;
        if(copyrightInt>10){
            Alert("恭喜你，解锁了神秘的版本号彩蛋！\n您当前的版本号是 "+version);
            copyrightInt = 0;
        }
    }

    /**
     * 获取版本号
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return "V"+version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void onImgClick() {
        imgInt++;
        allInt++;
        if(imgInt==1&&allInt>imgInt&&!firstOpen){
            Alert("您已连续点击LOGO "+allInt+"下\n真棒！请再接再厉！\n截屏分享给朋友，PK一下吧！");
        }else
        if(imgInt==10&&firstOpen){
            Alert("恭喜你，解锁了神奇的彩蛋，点击更多次获取更多的彩蛋吧！");
            SharedPreferencesUtil.put(this,"FIRSTOPEN",false);
        }else
        if ((imgInt<100&&imgInt % 10 == 0)||(imgInt<1000&&imgInt % 100 == 0)||(imgInt % 1000 == 0)) {
            Alert("恭喜你，达成成就:\n"+imgInt+"次LOGO连续点击！\n你总计已经点击"+allInt+"次了。希望再接再厉");
        }
    }

//    private void show(String s) {
//        new AlertDialog.Builder(this)
//                .setTitle(R.string.add_hint_title)
//                .setCancelable(true)
//                .setMessage(s)
//                .setPositiveButton("知道了", null)
//                .setIcon(R.mipmap.ic_launcher)
//                .show();
//    }
    private void copy() {
        myClip = ClipData.newPlainText("text", GitPath);
        myClipboard.setPrimaryClip(myClip);
    }

    @Override
    protected void onDestroy() {
        SharedPreferencesUtil.put(this,"ALLINT",allInt);
        super.onDestroy();
    }
}
