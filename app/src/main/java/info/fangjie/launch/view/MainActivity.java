package info.fangjie.launch.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jayfang.dropdownmenu.DropDownMenu;
import com.jayfang.dropdownmenu.OnMenuSelectedListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.common.App;
import info.fangjie.launch.common.CircleTransform;
import info.fangjie.launch.common.OrderOperation;
import info.fangjie.launch.model.OrderInfo;
import info.fangjie.launch.model.User;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private DrawerLayout drawerLayout;

    private SwipeRefreshLayout swipeLayout;

    private ListView listView;
    private OrderAdapter adpter;
    private List<OrderInfo> orders=new ArrayList<>();

    private DropDownMenu mMenu;

    private TextView userName;
    private ImageView userIcon,gender;
    final String[] strings=new String[]{"宿舍","性别","时间"};

    final String[] arr1=new String[]{"宿舍","韵苑","沁苑","紫松"};
    final String[] arr2=new String[]{"性别","男","女"};
    final String[] arr3=new String[]{"时间","中午","晚上"};

    private Handler mHandler ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawerLayout();
        initFab();

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void initView(){

        userName=(TextView)findViewById(R.id.username);
        userIcon=(ImageView)findViewById(R.id.avatar);
        gender=(ImageView)findViewById(R.id.iv_gender);

        userName.setText((((App) getApplication()).username));
        Picasso.with(this).load(User.getUserIconByName((((App) getApplication()).username))).
                transform(new CircleTransform()).into(userIcon);

        if (User.getUserSexByName((((App) getApplication()).username))==User.MALE){
            gender.setImageResource(R.drawable.ic_gender_male);
        }else{
            gender.setImageResource(R.drawable.ic_gender_famale);
        }

        ListView listView=(ListView)findViewById(R.id.listview);
        adpter=new OrderAdapter(this,orders);
        listView.setAdapter(adpter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", orders.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);


        mMenu=(DropDownMenu)findViewById(R.id.menu);
        mMenu.setmMenuCount(3);//Menu的个数
        mMenu.setmShowCount(6);//Menu展开list数量太多是只显示的个数
        mMenu.setShowCheck(true);//是否显示展开list的选中项
        mMenu.setmMenuTitleTextSize(16);//Menu的文字大小
        mMenu.setmMenuTitleTextColor(getResources().getColor(R.color.menu_text));//Menu的文字颜色
        mMenu.setmMenuListTextSize(16);//Menu展开list的文字大小
        mMenu.setmMenuListTextColor(getResources().getColor(R.color.menu_p_text));//Menu展开list的文字颜色
        mMenu.setmMenuBackColor(getResources().getColor(R.color.menu_back));//Menu的背景颜色
        mMenu.setmMenuPressedBackColor(getResources().getColor(R.color.menu_p_back));//Menu按下的背景颜色
        mMenu.setmCheckIcon(R.drawable.ico_make);//Menu展开list的勾选图片
        mMenu.setmUpArrow(R.drawable.arrow_up);//Menu默认状态的箭头
        mMenu.setmDownArrow(R.drawable.arrow_down);//Menu按下状态的箭头
        mMenu.setDefaultMenuTitle(strings);//默认未选择任何过滤的Menu title

        List<String[]> stringList=new ArrayList<>();
        stringList.add(arr1);
        stringList.add(arr2);
        stringList.add(arr3);
        mMenu.setmMenuItems(stringList);
        mMenu.setMenuSelectedListener(new OnMenuSelectedListener() {
            @Override
            //Menu展开的list点击事件  RowIndex：list的索引  ColumnIndex：menu的索引
            public void onSelected(View listview, int RowIndex, int ColumnIndex) {


            }
        });
    }

    public void initDrawerLayout(){
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView view = (NavigationView) findViewById(R.id.navigation_view);

        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override public boolean onNavigationItemSelected(MenuItem menuItem) {
                Intent intent=null;
                switch (menuItem.getItemId()){
                    case R.id.action_order:
                        intent=new Intent(MainActivity.this,HistoryActivity.class);
                        startActivity(intent);
                        menuItem.setChecked(true);
                        break;
                    case R.id.action_exit:
                        intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFab() {
        final FloatingActionButton floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PostOrderActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getData(){
        mHandler=new mHandler();
        OrderOperation orderOperation = new OrderOperation(mHandler);
        orderOperation.getAll();
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
                case OrderOperation.MSG_GETALL:
                    orders.clear();
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
                orders.clear();
                for (int i = 0; i < jsonDataArray.length(); i++)
                {
                    JSONObject tmpData = jsonDataArray.getJSONObject(i);

                    Log.i("get all data:", tmpData.toString());

                    OrderInfo tmpInfo = GetOrderInfoFromJson(tmpData);
                    orders.add(tmpInfo);
                }

                adpter.notifyDataSetChanged();
                swipeLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {
        getData();
    }
}
