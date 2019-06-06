package com.example.douyin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    /**
     * 注册
     */
    private TextView tv_register;

    /**
     * 请输入账号
     */
    private EditText et_user_name;
    /**
     * 请输入账号密码
     */
    private EditText et_pwd;
    /**
     * 找回密码
     */
    private TextView tv_findpwd;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getSupportActionBar().hide();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_register.setOnClickListener(this);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        tv_findpwd = (TextView) findViewById(R.id.tv_findpwd);
        tv_findpwd.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                break;
            case R.id.tv_register:
                break;
            case R.id.tv_findpwd:
                break;
            case R.id.btn_login:
                break;
        }
    }
}
