package xiaoliang.lnote.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import xiaoliang.lnote.R;
import xiaoliang.lnote.service.DataSyncService;
import xiaoliang.lnote.util.DataUtil;
import xiaoliang.lnote.util.SharedPreferencesUtil;

/**
 * 设置
 */
public class SettingsActivity extends BaseActivity implements Switch.OnCheckedChangeListener,View.OnClickListener {

    private Switch doubleExit,synchronizedToCalendar,reverseOrder;
    private View labelManagement,outputData,inputData;
    private static final int OUTPUT_CODE = 6814;
    private static final int INPUT_CODE = 6813;

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
                startActivity(new Intent(this,AmountStatusActivity.class));
                break;
            case R.id.setting_output_data:
//                checkOutput();
//                Alert(DataUtil.getDataPath(this));
                Alert("不知道什么原因，反正就是没折腾好，这块先放着吧！");
                break;
            case R.id.setting_input_data:
                Alert("同上，懒得打字了");
                break;
        }
    }

    private void checkOutput(){
        if(DataUtil.exists(this)){
            checkWriteSD(OUTPUT_CODE);
        }else{
            Alert("当前没有数据，无法导出！");
        }
    }

    @Override
    protected void onRequestWriteSD(int requestCode,boolean b) {
        switch (requestCode){
            case OUTPUT_CODE:
                if(b){
                    startService(new Intent(this,DataSyncService.class));
                }else{
                    Alert("对不起，权限不足，无法导出");
                }
                break;
        }
    }
}
