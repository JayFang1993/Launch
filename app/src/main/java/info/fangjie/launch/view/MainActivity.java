package info.fangjie.launch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jayfang.dropdownmenu.DropDownMenu;
import com.jayfang.dropdownmenu.OnMenuSelectedListener;

import java.util.ArrayList;
import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.model.Order;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private ListView listView;
    private OrderAdapter adpter;
    private List<Order> orders;

    private DropDownMenu mMenu;

    final String[] strings=new String[]{"配送位置","性别","时间"};

    final String[] arr1=new String[]{"韵苑","沁苑","紫松"};
    final String[] arr2=new String[]{"性别","男","女"};
    final String[] arr3=new String[]{"中午","晚上"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawerLayout();
        initFab();

        initView();
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
        ListView listView=(ListView)findViewById(R.id.listview);
        adpter=new OrderAdapter(this,null);
        listView.setAdapter(adpter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, OrderDetailActivity.class);
                startActivity(intent);
            }
        });

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
}
