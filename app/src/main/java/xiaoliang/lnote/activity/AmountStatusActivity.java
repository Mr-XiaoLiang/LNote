package xiaoliang.lnote.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import xiaoliang.lnote.R;
import xiaoliang.lnote.bean.AmountStatusBean;
import xiaoliang.lnote.listener.OnRecyclerItemClickListener;
import xiaoliang.lnote.util.DataUtil;
import xiaoliang.lnote.util.DatabaseHelper;

/**
 * Created by Administrator on 2016/6/20.
 * 金额类型管理页面
 */

public class AmountStatusActivity extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ArrayList<AmountStatusBean> data;
    private final static int GET = 200;
    private final static int DEL = 201;
    private AmountStatusAdapter adapter;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_amount_status);
        setSupportActionBar(toolbar);
        backEnable(true);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_amount_status_swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.activity_amount_status_recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.activity_amount_status_fab);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary);
        floatingActionButton.setOnClickListener(this);
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClick(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter = new AmountStatusAdapter());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_amount_status_fab:
                startActivity(new Intent(this,AddTypeActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData(){
        DataUtil.execute(new Get(this));
    }

    private void deleteItem(final int position){
        Alert("提示", "确定删除:" + data.get(position).name + "?", 0, true, "删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DataUtil.execute(new Delete(AmountStatusActivity.this,data.get(position).id));
            }
        });

    }

    private class Delete implements Runnable{

        private Context context;
        private int id;

        public Delete(Context context, int id) {
            this.context = context;
            this.id = id;
        }

        @Override
        public void run() {
            DatabaseHelper.delAmountStatus(context,id);
            Message message = new Message();
            message.what = DEL;
            message.obj = id;
            handler.sendEmptyMessage(DEL);
        }
    }

    private class Get implements Runnable{

        private Context context;

        public Get(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            data = DatabaseHelper.getAmountStatus(context);
            handler.sendEmptyMessage(GET);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GET:
                    if(data.size()<1){
                        T("没有数据");
                    }else{
                        recyclerView.notifyAll();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case DEL:
                    adapter.notifyItemRemoved((Integer) msg.obj);
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private class AmountStatusAdapter extends RecyclerView.Adapter<StatusHolder>{

        @Override
        public StatusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            StatusHolder holder = new StatusHolder(LayoutInflater.from(
                    AmountStatusActivity.this).inflate(R.layout.item_amount_status, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(StatusHolder holder, int position) {
            holder.init(data.get(position));
        }

        @Override
        public int getItemCount() {
            if(data==null)
                return 0;
            return data.size();
        }
    }

    private class StatusHolder extends RecyclerView.ViewHolder{
        View color;
        TextView text;

        public StatusHolder(View itemView) {
            super(itemView);
            color = itemView.findViewById(R.id.item_amount_status_color);
            text = (TextView) itemView.findViewById(R.id.item_amount_status_text);
        }
        public void init(AmountStatusBean bean){
            color.setBackgroundColor(bean.color);
            text.setText(bean.name);
        }
    }

    private class RecyclerViewItemClick extends OnRecyclerItemClickListener{

        public RecyclerViewItemClick(Context context) {
            super(context);
        }

        @Override
        public boolean onRecyclerItemClick(RecyclerView rv, RecyclerView.ViewHolder vh, int adapterPosition, int layoutPosition) {
            return super.onRecyclerItemClick(rv, vh, adapterPosition, layoutPosition);
        }

        @Override
        public void onRecyclerItemLongClick(RecyclerView rv, RecyclerView.ViewHolder vh, int adapterPosition, int layoutPosition) {
            T(adapterPosition+","+layoutPosition);
//            deleteItem(adapterPosition);
        }
    }
}
