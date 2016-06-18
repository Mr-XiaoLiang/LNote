package xiaoliang.lnote.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import xiaoliang.lnote.R;
import xiaoliang.lnote.service.LApplication;
import xiaoliang.lnote.util.SharedPreferencesUtil;

/**
 * Created by Administrator on 2016/6/13.
 * 所有页面的基类
 */

public class BaseActivity extends AppCompatActivity {

    private LApplication app;
    protected ImageLoader imageLoader;
    private Toast toast;
    /**
     * 写权限请求状态
     */
    protected static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    /**
     * 读权限请求状态
     */
    protected static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getThisTheme());
        imageLoader = ImageLoader.getInstance();
    }

    protected synchronized LApplication A(){
        if(app==null){
            app = (LApplication) getApplicationContext();
        }
        return app;
    }

    protected synchronized void T(String s){
        if(toast==null)
            toast = Toast.makeText(this,s,Toast.LENGTH_SHORT);
        else
            toast.setText(s);
        toast.show();
    }

    protected void Alert(String msg){
        Alert("提示",msg);
    }

    protected void Alert(String title, String msg){
        Alert(title,msg,0);
    }

    protected void Alert(String title, String msg,
                         int icon){
        Alert(title,msg,icon,true,"知道了",null);
    }

    protected void Alert(String title, String msg,
                         int icon, boolean cancelable,
                         String positiveStr, DialogInterface.OnClickListener positiveClick){
        Alert(title,msg,icon,cancelable,"",null,positiveStr,positiveClick);
    }

    protected void Alert(String title, String msg,
                         int icon, boolean cancelable,
                         String negativeStr, DialogInterface.OnClickListener negativeClick,
                         String positiveStr, DialogInterface.OnClickListener positiveClick){
        new AlertDialog.Builder(this)
                .setMessage(msg).
                setTitle(title)
                .setIcon(icon)
                .setCancelable(cancelable)
                .setNegativeButton(negativeStr,negativeClick)
                .setPositiveButton(positiveStr,positiveClick)
                .show();
    }

    protected int getThisTheme(){
        return SharedPreferencesUtil.getTheme(this);
    }
    protected void backEnable(boolean b){
        if(b&&getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }else if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void checkWriteSD(int requestCode){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    makePermissionsCode(requestCode,WRITE_EXTERNAL_STORAGE_REQUEST_CODE));
        }else{
            onRequestWriteSD(requestCode,true);
        }
    }
    protected void checkReadSD(int requestCode){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    makePermissionsCode(requestCode,READ_EXTERNAL_STORAGE_REQUEST_CODE));
        }else{
            onRequestWriteSD(requestCode,true);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (getMode(requestCode)){
            case WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                onRequestWriteSD(getCode(requestCode),grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
            case READ_EXTERNAL_STORAGE_REQUEST_CODE:
                onRequestReadSD(getCode(requestCode),grantResults[0] == PackageManager.PERMISSION_GRANTED);
                break;
        }
    }

    protected void onRequestWriteSD(int requestCode,boolean b){

    }

    protected void onRequestReadSD(int requestCode,boolean b){

    }

    /**
     * 模仿View的测量方法，将状态与请求code进行合并
     * @param code 请求码
     * @param mode 类型码
     * @return 权限请求码
     */
    public static int makePermissionsCode(int code, int mode) {
        if(code>9999)
            throw new RuntimeException("code have to <10000");
        return mode*10000+code;
    }

    /**
     * 获取状态
     * @param permissionsCode 权限请求码
     * @return
     */
    public static int getMode(int permissionsCode) {
        return permissionsCode/10000;
    }

    /**
     * 获取请求code
     * @param permissionsCode 权限请求码
     * @return
     */
    public static int getCode(int permissionsCode) {
        return permissionsCode%10000;
    }

}
