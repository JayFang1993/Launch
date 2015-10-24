package info.fangjie.launch.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.fangjie.launch.R;

/**
 * Created by FangJie on 15/10/24.
 */
public class PostOrderActivity extends AppCompatActivity {

    private EditText editTextTitle,editTextDetail,editTextShitang,editTextTime,editTextDes,editTextPhone,editTextAward;

    private ImageView imageViewVoice,imageViewShitang,imageViewTime,imageViewAward;

    private PopupWindow popupWindow;
    private ListView listView;

    private ArrayAdapter adapterShitang,adapterTime,adapterAward;
    private List<String> listShitang=new ArrayList<>();
    private List<String> listTime=new ArrayList<>();
    private List<String> listAward=new ArrayList<>();
    private MaterialDialog materialDialog ;

    private int showIndex=-1;

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
        editTextShitang=(EditText)findViewById(R.id.et_shitang);
        editTextTime=(EditText)findViewById(R.id.et_time);
        editTextAward=(EditText)findViewById(R.id.et_award);

        imageViewAward=(ImageView)findViewById(R.id.iv_award);
        imageViewVoice=(ImageView)findViewById(R.id.iv_voice);
        imageViewShitang=(ImageView)findViewById(R.id.iv_shitang);
        imageViewTime=(ImageView)findViewById(R.id.iv_time);

        materialDialog = new MaterialDialog(this);
        materialDialog.setCanceledOnTouchOutside(true);

//        final View popupView=LayoutInflater.from(this).inflate(R.layout.popwindow, null);
        listView= new ListView(this);
        listView.setDivider(null);
        listView.setVerticalScrollBarEnabled(false);
//        popupWindow=new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
//        popupWindow.setTouchable(true);
//        popupWindow.setOutsideTouchable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

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


    class PopWindowAdapter extends BaseAdapter {

        private List<String> datas;

        PopWindowAdapter(List<String> datas) {
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(PostOrderActivity.this).inflate(R.layout.item_popwindow, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.tv_poptitle);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.textView.setText(datas.get(position));
            }
            return convertView;
        }

        class ViewHolder {
            private TextView textView;
        }
    }
}
