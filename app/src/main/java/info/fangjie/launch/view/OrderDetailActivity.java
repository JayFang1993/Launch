package info.fangjie.launch.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import info.fangjie.launch.R;
import info.fangjie.launch.common.App;
import info.fangjie.launch.common.CircleTransform;
import info.fangjie.launch.common.OrderOperation;
import info.fangjie.launch.model.OrderInfo;
import info.fangjie.launch.model.User;

/**
 * Created by FangJie on 15/10/24.
 */
public class OrderDetailActivity extends AppCompatActivity {

    private TextView textViewTitle,textViewSushe,textViewShitang,textViewTime,textViewAward,textViewDetail,textViewCallme;
    private Button btnSend;
    private OrderInfo orderInfo;

    private ImageView icon1,icon2,icon3;
    private RelativeLayout relativeLayout2;
    private LinearLayout linearLayout3;

    private Handler mHandler ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetail);
        initToolbar();

        orderInfo=(OrderInfo)getIntent().getSerializableExtra("order");

//        ((App)getApplication()).username;



        initView();
    }

    private void initView(){
        btnSend=(Button)findViewById(R.id.btn_send);
        relativeLayout2=(RelativeLayout)findViewById(R.id.rl_talk2);
        linearLayout3=(LinearLayout)findViewById(R.id.ll_talk3);
        textViewTitle=(TextView)findViewById(R.id.tv_title);
        textViewAward=(TextView)findViewById(R.id.tv_award);
        textViewSushe=(TextView)findViewById(R.id.tv_sushe);
        textViewShitang=(TextView)findViewById(R.id.tv_shitang);
        textViewTime=(TextView)findViewById(R.id.tv_time);
        textViewDetail=(TextView)findViewById(R.id.tv_detail);
        icon1=(ImageView)findViewById(R.id.iv_icon1);
        icon2=(ImageView)findViewById(R.id.iv_icon2);
        icon3=(ImageView)findViewById(R.id.iv_icon3);
        textViewCallme=(TextView)findViewById(R.id.tv_phone);


        textViewTitle.setText(orderInfo.title);
        textViewTime.setText(orderInfo.time);
        textViewShitang.setText(orderInfo.canteen);
        textViewSushe.setText(orderInfo.dst);
        textViewAward.setText(orderInfo.pay);
        textViewDetail.setText(orderInfo.info);
        Picasso.with(this).load(User.getUserIconByName(orderInfo.userfrom)).transform(new CircleTransform()).into(icon1);

        if (orderInfo.status== OrderOperation.STATUS_NOT){
            relativeLayout2.setVisibility(View.GONE);
            btnSend.setText("我送");
            btnSend.setEnabled(true);
            btnSend.setBackgroundResource(R.drawable.ic_send);
            linearLayout3.setVisibility(View.GONE);
        }else if (orderInfo.status== OrderOperation.STATUS_ING){
            relativeLayout2.setVisibility(View.VISIBLE);
            btnSend.setText("已被接");
            btnSend.setEnabled(false);
            linearLayout3.setVisibility(View.VISIBLE);
            btnSend.setBackgroundResource(R.drawable.ic_send_grey);
            textViewCallme.setText("Call Me："+User.getUserPhoneByName(orderInfo.userfrom));
            Picasso.with(this).load(User.getUserIconByName(orderInfo.userfrom)).transform(new CircleTransform()).into(icon3);
            Picasso.with(this).load(User.getUserIconByName(orderInfo.userto)).transform(new CircleTransform()).into(icon2);
        }else {
            relativeLayout2.setVisibility(View.VISIBLE);
            btnSend.setText("已完成");
            btnSend.setEnabled(false);
            linearLayout3.setVisibility(View.VISIBLE);
            btnSend.setBackgroundResource(R.drawable.ic_send_grey);
            textViewCallme.setText("Call Me："+User.getUserPhoneByName(orderInfo.userfrom));
            Picasso.with(this).load(User.getUserIconByName(orderInfo.userfrom)).transform(new CircleTransform()).into(icon3);
            Picasso.with(this).load(User.getUserIconByName(orderInfo.userto)).transform(new CircleTransform()).into(icon2);
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderInfo.userfrom.equals(((App)getApplication()).username)){
                    Toast.makeText(OrderDetailActivity.this,"你自己叫的饭，不能自己接哦^_^",Toast.LENGTH_LONG).show();
                }else{
                    takeOrder();
                }
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


    private void takeOrder(){
        mHandler=new mHandler();
        OrderOperation orderOperation = new OrderOperation(mHandler);
        orderOperation.updateStatus(orderInfo.id,((App)getApplication()).username,OrderOperation.STATUS_ING);
        finish();
    }

    class mHandler extends Handler{

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
                default:
                    break;
            }
        }

    }
}
