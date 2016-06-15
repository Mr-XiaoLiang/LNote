package xiaoliang.lnote.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import xiaoliang.lnote.R;
import xiaoliang.lnote.util.TextToColor;

public class AddTypeActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener,TextWatcher {

    private AppCompatAutoCompleteTextView nameText;
    private View showView;
    private SeekBar redBar,greenBar,blueBar,alphaBar;
    private int color = Color.WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_add);
        setSupportActionBar(toolbar);
        getWidget();
        backEnable(true);
    }

    private void getWidget(){
        nameText = (AppCompatAutoCompleteTextView) findViewById(R.id.add_type_name);
        showView = findViewById(R.id.add_type_show);
        redBar = (SeekBar) findViewById(R.id.add_type_red);
        greenBar = (SeekBar) findViewById(R.id.add_type_green);
        blueBar = (SeekBar) findViewById(R.id.add_type_blue);
        alphaBar = (SeekBar) findViewById(R.id.add_type_alpha);
        redBar.setOnSeekBarChangeListener(this);
        greenBar.setOnSeekBarChangeListener(this);
        blueBar.setOnSeekBarChangeListener(this);
        alphaBar.setOnSeekBarChangeListener(this);
        nameText.addTextChangedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_type_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_done:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.add_hint_title)
                        .setMessage("你点了确定，然而什么也没有发生！\n因为我还没有做提交方法。")
                        .setCancelable(true)
                        .setPositiveButton("知道了",null)
                        .show();
                break;
            case R.id.menu_refresh:
                if(TextUtils.isEmpty(nameText.getText().toString())){
                    showColor(Color.TRANSPARENT);
                }else{
                    forColor(nameText.getText().toString());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void forColor(String text){
        showColor(TextToColor.format(text));
    }

    private void showColor(int c){
        color = c;
        alphaBar.setProgress(Color.alpha(color));
        redBar.setProgress(Color.red(color));
        greenBar.setProgress(Color.green(color));
        blueBar.setProgress(Color.blue(color));
        showView.setBackgroundColor(color);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()){
            case R.id.add_type_alpha:
                color = Color.argb(progress,Color.red(color),Color.green(color),Color.blue(color));
                break;
            case R.id.add_type_red:
                color = Color.argb(Color.alpha(color),progress,Color.green(color),Color.blue(color));
                break;
            case R.id.add_type_green:
                color = Color.argb(Color.alpha(color),Color.red(color),progress,Color.blue(color));
                break;
            case R.id.add_type_blue:
                color = Color.argb(Color.alpha(color),Color.red(color),Color.green(color),progress);
                break;
        }
        showView.setBackgroundColor(color);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(TextUtils.isEmpty(s.toString())){
            showColor(Color.TRANSPARENT);
            return;
        }
        forColor(s.toString());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void afterTextChanged(Editable s) {}
}
