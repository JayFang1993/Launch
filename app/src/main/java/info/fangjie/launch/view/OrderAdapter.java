package info.fangjie.launch.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.common.CircleTransform;
import info.fangjie.launch.model.OrderInfo;
import info.fangjie.launch.model.User;

/**
 * Created by FangJie on 15/10/24.
 */
public class OrderAdapter extends BaseAdapter {

    private List<OrderInfo> orders;
    private Context context;

    OrderAdapter(Context context,List<OrderInfo> orders){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview, null);
            holder = new ViewHolder();
            holder.textViewDesc=(TextView)convertView.findViewById(R.id.tv_item_futitle);
            holder.textViewTitle=(TextView)convertView.findViewById(R.id.tv_item_title);
            holder.imageView=(ImageView)convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(context).load(User.getUserIconByName(orders.get(position).userfrom)).transform(new CircleTransform()).into(holder.imageView);
        holder.textViewTitle.setText(orders.get(position).title);
        String futitle=orders.get(position).canteen+";"+orders.get(position).time+","+orders.get(position).dst;
        holder.textViewDesc.setText(futitle);
        return convertView;
    }


    class ViewHolder{
        private TextView textViewTitle;
        private ImageView imageView;
        private TextView textViewDesc;

    }
}
