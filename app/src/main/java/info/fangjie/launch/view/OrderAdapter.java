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
import info.fangjie.launch.model.Order;

/**
 * Created by FangJie on 15/10/24.
 */
public class OrderAdapter extends BaseAdapter {

    private List<Order> orders;
    private Context context;

    OrderAdapter(Context context,List<Order> orders){
        this.orders=orders;
        this.context=context;
    }

    @Override
    public int getCount() {
        return 8;
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
            holder.imageView=(ImageView)convertView.findViewById(R.id.iv_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        Picasso.with(context).load(R.drawable.icon).transform(new CircleTransform()).into(holder.imageView);

        return convertView;
    }


    class ViewHolder{
        private TextView textView;
        private ImageView imageView;

    }
}
