package xiaoliang.lnote.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import xiaoliang.lnote.R;
import xiaoliang.lnote.util.SharedPreferencesUtil;

public class AddActivity extends BaseActivity implements View.OnClickListener {

    private TextView dateText,timeText;
    private CheckBox remindmeBox;
    private RadioGroup cashierTypeGroup;
    private AppCompatAutoCompleteTextView amountEdit,noteEdit;
    private int year,month,day,hours,minutes;
    private SimpleDateFormat simpleDateFormat;
    private Calendar calendar,nowCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_add);
        setSupportActionBar(toolbar);
        backEnable(true);
        getWidget();
        initData();
        hint();
    }
    //初始化数据
    private void initData(){
        simpleDateFormat = new SimpleDateFormat();
        calendar = Calendar.getInstance();
        nowCalendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
        setDate();
    }
    //设置显示时间
    private void setDate(){
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,day);
        calendar.set(Calendar.HOUR_OF_DAY,hours);
        calendar.set(Calendar.MINUTE,minutes);
        nowCalendar.setTime(new Date());
        if(calendar.after(nowCalendar)){
            remindmeBox.setVisibility(View.VISIBLE);
        }else{
            remindmeBox.setVisibility(View.GONE);
        }
        if(nowCalendar.get(Calendar.YEAR)!=calendar.get(Calendar.YEAR))
            simpleDateFormat.applyPattern("yyyy/MM/dd");
        else
            simpleDateFormat.applyPattern("MM/dd");
        dateText.setText(simpleDateFormat.format(calendar.getTime()));
        simpleDateFormat.applyPattern("HH:mm");
        timeText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void getWidget(){
        dateText = (TextView) findViewById(R.id.add_date);
        timeText = (TextView) findViewById(R.id.add_time);
        remindmeBox = (CheckBox) findViewById(R.id.add_remindme);
        cashierTypeGroup = (RadioGroup) findViewById(R.id.cashier_type);
        amountEdit = (AppCompatAutoCompleteTextView) findViewById(R.id.add_amount);
        noteEdit = (AppCompatAutoCompleteTextView) findViewById(R.id.add_note);
        dateText.setOnClickListener(this);
        timeText.setOnClickListener(this);
        amountEdit.addTextChangedListener(amountEditWatcher);
    }

    private TextWatcher amountEditWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(TextUtils.isEmpty(s.toString())){
                cashierTypeGroup.setVisibility(View.GONE);
            }else{
                cashierTypeGroup.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    private void getDateDialog(int y,int m,int d){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                AddActivity.this.year = year;
                month = monthOfYear;
                day = dayOfMonth;
                setDate();
            }
        },y,m,d);
        datePickerDialog.show();
    }

    private void getTimeDialog(int h,int m){
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                hours = hourOfDay;
                minutes = minute;
                setDate();
            }
        },h,m,true);
        timePickerDialog.show();
    }

    private void hint(){
        if((Boolean)SharedPreferencesUtil.get(this,"showHint",true)){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.add_hint_title)
                    .setMessage(R.string.add_hint_msg)
                    .setCancelable(true)
                    .setPositiveButton("知道了",null)
                    .setNeutralButton("不再提醒", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferencesUtil.put(AddActivity.this,"showHint",false);
                        }
                    }).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_done:
                startActivity(new Intent(this,AddTypeActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_date:
                getDateDialog(year,month,day);
                break;
            case R.id.add_time:
                getTimeDialog(hours,minutes);
                break;
        }
    }
}
