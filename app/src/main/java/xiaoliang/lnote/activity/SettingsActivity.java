package xiaoliang.lnote.activity;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import xiaoliang.lnote.R;

/**
 * 设置
 */
public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_settings);
        setSupportActionBar(toolbar);
        backEnable(true);
    }
}
