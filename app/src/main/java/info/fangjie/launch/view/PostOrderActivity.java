package info.fangjie.launch.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.common.App;
import info.fangjie.launch.model.OrderInfo;
import info.fangjie.launch.common.OrderOperation;
import info.fangjie.launch.model.User;

/**
 * Created by FangJie on 15/10/24.
 */
public class PostOrderActivity extends AppCompatActivity {

    private EditText editTextTitle,editTextDetail,editTextShitang,editTextTime,editTextDes,editTextPhone,editTextAward;

    private ImageView imageViewVoice,imageViewShitang,imageViewTime,imageViewAward;

    private PopupWindow popupWindow;
    private ListView listView;
    private ImageView btnSend;

    private ArrayAdapter adapterShitang,adapterTime,adapterAward;
    private List<String> listShitang=new ArrayList<>();
    private List<String> listTime=new ArrayList<>();
    private List<String> listAward=new ArrayList<>();
    private MaterialDialog materialDialog ;

    private int showIndex=-1;

    private Handler mHandler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postorder);
        initToolbar();
        initView();

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


    private void initView(){

        editTextTitle=(EditText)findViewById(R.id.et_title);
        editTextDes=(EditText)findViewById(R.id.et_des);
        editTextDetail=(EditText)findViewById(R.id.et_detail);
        editTextPhone=(EditText)findViewById(R.id.et_phone);
        editTextPhone.setText(User.getUserPhoneByName(((App) getApplication()).username));
        editTextShitang=(EditText)findViewById(R.id.et_shitang);
        editTextTime=(EditText)findViewById(R.id.et_time);
        editTextAward=(EditText)findViewById(R.id.et_award);
        btnSend=(ImageView)findViewById(R.id.btn_send);

        imageViewAward=(ImageView)findViewById(R.id.iv_award);
        imageViewVoice=(ImageView)findViewById(R.id.iv_voice);
        imageViewShitang=(ImageView)findViewById(R.id.iv_shitang);
        imageViewTime=(ImageView)findViewById(R.id.iv_time);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderInfo orderInfo=new OrderInfo();
                orderInfo.canteen=editTextShitang.getText().toString();
                orderInfo.dst=editTextDes.getText().toString();
                orderInfo.info=editTextDetail.getText().toString();
                orderInfo.pay=editTextAward.getText().toString();
                orderInfo.tel=editTextPhone.getText().toString();
                orderInfo.time=editTextTime.getText().toString();
                orderInfo.userfrom=((App)getApplication()).username;
                orderInfo.title=editTextTitle.getText().toString();
                addOrder(orderInfo);

                finish();
            }
        });

        materialDialog = new MaterialDialog(this);
        materialDialog.setCanceledOnTouchOutside(true);

        listView= new ListView(this);
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);

        listShitang.add("东一食堂");
        listShitang.add("东三食堂");
        listShitang.add("百景园");
        listShitang.add("百惠园");
        listShitang.add("西一食堂");
        listShitang.add("西二食堂");

        listTime.add("11:00-12:00");
        listTime.add("12:00-13:00");
        listTime.add("17:00-18:00");
        listTime.add("18:00-19:00");

        listAward.add("妹子卖个萌");
        listAward.add("送妹子电话、微信");
        listAward.add("请喝饮料");
        listAward.add("下次给你带");
        listAward.add("给你一块钱跑路费");

        editTextShitang.setText(listShitang.get(0));
        editTextTime.setText(listTime.get(0));
        editTextAward.setText(listAward.get(0));

        imageViewShitang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterShitang = new ArrayAdapter<String>(
                        PostOrderActivity.this,
                        R.layout.item_popwindow,
                        R.id.tv_poptitle,
                        listShitang);
                listView.setAdapter(adapterShitang);
                materialDialog.setContentView(listView);
                materialDialog.show();
                showIndex=1;
            }
        });
        imageViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterTime = new ArrayAdapter<String>(
                        PostOrderActivity.this,
                        R.layout.item_popwindow,
                        R.id.tv_poptitle,
                        listTime);
                listView.setAdapter(adapterTime);
                materialDialog.setContentView(listView);
                materialDialog.show();
                showIndex=2;
            }
        });

        imageViewAward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapterAward = new ArrayAdapter<String>(
                        PostOrderActivity.this,
                        R.layout.item_popwindow,
                        R.id.tv_poptitle,
                        listAward);
                listView.setAdapter(adapterAward);
                materialDialog.setContentView(listView);
                materialDialog.show();
                showIndex=3;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (showIndex==1){
                    editTextShitang.setText(listShitang.get(position));
                }else if (showIndex==2){
                    editTextTime.setText(listTime.get(position));
                }else if (showIndex==3){
                    editTextAward.setText(listAward.get(position));
                }else{
                    materialDialog.dismiss();
                }
                materialDialog.dismiss();
            }
        });


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



    private void addOrder(OrderInfo orderInfo){
        mHandler=new mHandler();
        OrderOperation orderOperation = new OrderOperation(mHandler);
        orderOperation.addOrder(orderInfo);
    }

    class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            JSONObject jsonData = (JSONObject) msg.obj;
            int msgtype = 0;
            try {
                msgtype = jsonData.getInt("msgtype");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            switch (msgtype) {

                default:
                    break;
            }
        }
    }
}
