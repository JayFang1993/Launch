package info.fangjie.launch.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.common.App;
import info.fangjie.launch.common.CircleTransform;
import info.fangjie.launch.model.OrderInfo;
import info.fangjie.launch.model.User;

/**
 * Created by FangJie on 15/10/24.
 */
public class HistoryAdapter extends BaseAdapter {

    private List<OrderInfo> orders;
    private Context context;

    HistoryAdapter(Context context, List<OrderInfo> orders){
        this.orders=orders;
        this.context=context;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_history, null);
            holder = new ViewHolder();
            holder.meIcon=(ImageView)convertView.findViewById(R.id.iv_icon);
            holder.meTitle=(TextView)convertView.findViewById(R.id.tv_metitle);
            holder.meShitang=(TextView)convertView.findViewById(R.id.tv_me_shitang);
            holder.meSushe=(TextView)convertView.findViewById(R.id.tv_me_sushe);
            holder.me=(RelativeLayout)convertView.findViewById(R.id.rl_me);
            holder.his=(RelativeLayout)convertView.findViewById(R.id.rl_his);
            holder.hisShitang=(TextView)convertView.findViewById(R.id.tv_his_shitang);
            holder.hisIcon=(ImageView)convertView.findViewById(R.id.iv_his_icon);
            holder.hisTitle=(TextView)convertView.findViewById(R.id.tv_his_title);
            holder.hisSushe=(TextView)convertView.findViewById(R.id.tv_his_sushe);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (orders.get(position).userto.equals(((App)(((Activity)context).getApplication())).username)){
            holder.me.setVisibility(View.GONE);
            holder.his.setVisibility(View.VISIBLE);
            holder.hisTitle.setText(orders.get(position).title);
            holder.hisSushe.setText(orders.get(position).dst);
            holder.hisShitang.setText(orders.get(position).canteen);
            Picasso.with(context).load(User.getUserIconByName(orders.get(position).userfrom)).transform(new CircleTransform()).into(holder.hisIcon);
        }else {
            holder.his.setVisibility(View.GONE);
            holder.me.setVisibility(View.VISIBLE);
            holder.meTitle.setText(orders.get(position).title);
            holder.meSushe.setText(orders.get(position).dst);
            holder.meShitang.setText(orders.get(position).canteen);
            Picasso.with(context).load(User.getUserIconByName(orders.get(position).userto)).transform(new CircleTransform()).into(holder.meIcon);
        }
        return convertView;
    }


    class ViewHolder{
        private RelativeLayout me;
        private RelativeLayout his;

        private TextView meTitle;
        private ImageView meIcon;
        private TextView meShitang;
        private TextView meSushe;

        private TextView hisTitle;
        private TextView hisShitang;
        private TextView hisSushe;
        private ImageView hisIcon;


    }
}
