package info.fangjie.launch.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import info.fangjie.launch.R;
import info.fangjie.launch.common.App;

/**
 * Created by FangJie on 15/10/24.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText username,passwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initToolbar();
        initView();

    }

    private void initView(){
        btnLogin=(Button)findViewById(R.id.btn_login);
        username=(EditText)findViewById(R.id.et_username);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String input=username.getEditableText().toString();

                if (!input.equals("JayFang")&&!input.equals("xiaoming")&&!input.equals("Android")&&!input.equals("eclipse")){
                    Toast.makeText(LoginActivity.this,"你输入的用户名不正确",Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    ((App)getApplication()).username=input;
                    finish();
                }

            }
        });


    }

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }


}
