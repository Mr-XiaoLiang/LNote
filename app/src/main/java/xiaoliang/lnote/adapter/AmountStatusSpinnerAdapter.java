package xiaoliang.lnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import xiaoliang.lnote.R;
import xiaoliang.lnote.bean.AmountStatusBean;

/**
 * Created by LiuJ on 2016/6/18.
 * 金额类型的下拉列表适配器
 */
public class AmountStatusSpinnerAdapter extends BaseAdapter{

    private ArrayList<AmountStatusBean> data;
    private LayoutInflater inflater;

    public AmountStatusSpinnerAdapter(Context context, ArrayList<AmountStatusBean> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_amount_status_spinner,null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        holder.init(data.get(position));
        return convertView;
    }

    private class Holder {
        View color;
        TextView text;

        public Holder(View convertView) {
            color = convertView.findViewById(R.id.item_amount_status_spinner_color);
            text = (TextView) convertView.findViewById(R.id.item_amount_status_spinner_text);
        }
        public void init(AmountStatusBean bean){
            color.setBackgroundColor(bean.color);
            text.setText(bean.name);
        }
    }

}
