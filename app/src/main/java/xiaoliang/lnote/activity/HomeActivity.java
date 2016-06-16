package xiaoliang.lnote.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import xiaoliang.lnote.R;
import xiaoliang.lnote.util.SharedPreferencesUtil;

public class HomeActivity extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener,SearchView.OnQueryTextListener {

    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private boolean doubleExit = true;
    private static Boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_home);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.home_recycler_view);
        swipeRefreshLayout.setOnRefreshListener(this);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting:
                startActivity(new Intent(this,SettingsActivity.class));
                return true;
            case R.id.menu_help:
                showHelp();
                return true;
            case R.id.menu_about:
                startActivity(new Intent(this,AboutActivity.class));
                return true;
            case R.id.menu_finish:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelp(){
        new AlertDialog.Builder(this)
                .setTitle(R.string.add_hint_title)
                .setMessage(R.string.help)
                .setCancelable(true)
                .setPositiveButton("知道了",null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        final MenuItem item = menu.findItem(R.id.menu_search);
        searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        SearchView.SearchAutoComplete edit = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        edit.setHintTextColor(Color.WHITE);
        edit.setBackground(getResources().getDrawable(R.drawable.textfield_multiline_disabled_holo_light));
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                startActivity(new Intent(this,AddActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(doubleExit){
            exitBy2Click();
        }else{
            finish();
        }
    }

    private void exitBy2Click() {
        Timer tExit = null;
        if (!isExit) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        doubleExit = SharedPreferencesUtil.getDoubleExit(this);
    }

    @Override
    public void onRefresh() {

    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
