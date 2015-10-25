package info.fangjie.launch.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.common.App;
import info.fangjie.launch.common.OrderOperation;
import info.fangjie.launch.model.OrderInfo;

/**
 * Created by FangJie on 15/10/24.
 */
public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private HistoryAdapter adpter;
    private List<OrderInfo> datas=new ArrayList<>();

    private Handler mHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initToolbar();

        initView();

        getData();
    }

    private void initView(){
        listView=(ListView)findViewById(R.id.lv_history);
        adpter=new HistoryAdapter(this,datas);
        listView.setAdapter(adpter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HistoryActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", datas.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getData(){
        mHandler=new mHandler();
        OrderOperation orderOperation = new OrderOperation(mHandler);
        orderOperation.getFinish(((App)getApplication()).username);
    }

    class mHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            JSONObject jsonData = (JSONObject)msg.obj;
            int msgtype = 0;
            try {
                msgtype = jsonData.getInt("msgtype");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            switch (msgtype) {
                case OrderOperation.MSG_FINISH:
                    datas.clear();
                    handleGetAll(jsonData);
                    break;

                default:
                    break;
            }
        }

        void handleGetAll(JSONObject jsonData)
        {
            try {
                JSONArray jsonDataArray = jsonData.getJSONArray("data");
                for (int i = 0; i < jsonDataArray.length(); i++)
                {
                    JSONObject tmpData = jsonDataArray.getJSONObject(i);
                    Log.i("get all data:", tmpData.toString());
                    OrderInfo tmpInfo = GetOrderInfoFromJson(tmpData);
                    datas.add(tmpInfo);
                }
                adpter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        OrderInfo GetOrderInfoFromJson(JSONObject tmpData)
        {
            OrderInfo tmpInfo = new OrderInfo();

            try {
                tmpInfo.id = tmpData.getInt("id");
                tmpInfo.title = tmpData.getString("title");
                tmpInfo.info = tmpData.getString("info");
                tmpInfo.time = tmpData.getString("time");
                tmpInfo.canteen = tmpData.getString("canteen");
                tmpInfo.dst = tmpData.getString("dst");
                tmpInfo.tel = tmpData.getString("tel");
                tmpInfo.pay = tmpData.getString("pay");
                tmpInfo.status = tmpData.getInt("status");
                tmpInfo.userfrom = tmpData.getString("userfrom");
                tmpInfo.userto = tmpData.getString("userto");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return tmpInfo;
        }
    }
}
