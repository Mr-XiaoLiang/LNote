package xiaoliang.lnote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

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
}
