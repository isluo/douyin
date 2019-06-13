package com.example.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.User;

import org.json.JSONObject;

import java.util.Map;

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
    private String userName,password;
    boolean iset1,iset2;
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

        etChangLisener();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.tv_findpwd:
                break;
            case R.id.btn_login:

               userName = et_user_name.getText().toString();
               password = et_pwd.getText().toString();
                MyVolley.B.login.exec(userName,password).exec(this);
                break;
        }
    }

    public void login(JSONObject jsonObject){

            Map<String, Object> maps = GetDate.getLogin(jsonObject);
            Log.e("AAAAA", maps.get("msg").toString());
            if ((boolean) maps.get("msg")) {
                User user = (User) maps.get("user");
                App.user = user.getUsername();
                App.head = ImgPath.getImgs(user.getHead());
                App.nname = user.getNname();
                App.introduce = user.getIntroduce();
                Log.e("user", "" + user.toString());
                Log.e("user", "" + App.user+"::"+App.head);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            } else {
                Log.e("CCC", "登录失败" + maps.get("ERROR").toString());
                Toast.makeText(this,"登录失败" + maps.get("ERROR").toString(),Toast.LENGTH_SHORT).show();
            }
    }



    private void etChangLisener() {
        et_user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    iset1 = true;
                }else {
                    iset1 = false;
                }
                if(iset1&&iset2){
                    btn_login.setEnabled(true);
                }else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    iset2 = true;
                }else {
                    iset2 = false;
                }
                if(iset1&&iset2){
                    btn_login.setEnabled(true);
                }else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
