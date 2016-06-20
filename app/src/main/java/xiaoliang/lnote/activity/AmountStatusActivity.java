package xiaoliang.lnote.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import xiaoliang.lnote.R;

/**
 * Created by Administrator on 2016/6/20.
 * 金额类型管理页面
 */

public class AmountStatusActivity extends BaseActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_amount_status);
        setSupportActionBar(toolbar);
        backEnable(true);
    }

    @Override
    public void onClick(View v) {

    }
}
