package info.fangjie.launch.view;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import info.fangjie.launch.R;
import info.fangjie.launch.model.Order;

/**
 * Created by FangJie on 15/10/24.
 */
public class HistoryActivity extends AppCompatActivity {

    private ListView listView;
    private HistoryAdapter adpter;
    private List<Order> datas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        initToolbar();

        initView();

    }

    private void initView(){
        listView=(ListView)findViewById(R.id.lv_history);
        adpter=new HistoryAdapter(this,null);
        listView.setAdapter(adpter);

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

}
