package xiaoliang.lnote.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import xiaoliang.lnote.R;

public class AddActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backEnable(true);
    }
}
