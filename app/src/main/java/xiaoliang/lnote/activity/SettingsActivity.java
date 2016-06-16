package xiaoliang.lnote.activity;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import xiaoliang.lnote.R;
import xiaoliang.lnote.util.SharedPreferencesUtil;

/**
 * 设置
 */
public class SettingsActivity extends BaseActivity implements Switch.OnCheckedChangeListener,View.OnClickListener {

    private Switch doubleExit,synchronizedToCalendar,reverseOrder;
    private View labelManagement,outputData,inputData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_settings);
        setSupportActionBar(toolbar);
        backEnable(true);
        doubleExit = (Switch) findViewById(R.id.setting_double_exit);
        synchronizedToCalendar = (Switch) findViewById(R.id.setting_synchronized_to_calendar);
        reverseOrder = (Switch) findViewById(R.id.setting_reverse_order);
        labelManagement = findViewById(R.id.setting_label_management);
        outputData = findViewById(R.id.setting_output_data);
        inputData = findViewById(R.id.setting_input_data);
        doubleExit.setOnCheckedChangeListener(this);
        synchronizedToCalendar.setOnCheckedChangeListener(this);
        reverseOrder.setOnCheckedChangeListener(this);
        labelManagement.setOnClickListener(this);
        outputData.setOnClickListener(this);
        inputData.setOnClickListener(this);
        init();
    }

    private void init(){
        doubleExit.setChecked(SharedPreferencesUtil.getDoubleExit(this));
        synchronizedToCalendar.setChecked(SharedPreferencesUtil.getSynchronizedToCalendar(this));
        reverseOrder.setChecked(SharedPreferencesUtil.getReverseOrder(this));
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.setting_double_exit:
                SharedPreferencesUtil.setDoubleExit(this,isChecked);
                break;
            case R.id.setting_synchronized_to_calendar:
                SharedPreferencesUtil.setSynchronizedToCalendar(this,isChecked);
                break;
            case R.id.setting_reverse_order:
                SharedPreferencesUtil.setReverseOrder(this,isChecked);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_label_management:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.add_hint_title)
                        .setCancelable(true)
                        .setMessage("还没有写这个功能，所以弹出一句话，给你看看。这不是bug，只是功能没写！")
                        .setPositiveButton("知道了", null)
                        .setIcon(R.mipmap.ic_launcher)
                        .show();
                break;
            case R.id.setting_output_data:
                break;
            case R.id.setting_input_data:
                break;
        }
    }



}
