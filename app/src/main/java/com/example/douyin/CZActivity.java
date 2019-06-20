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
import android.widget.Toast;

import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;

import org.json.JSONObject;

import java.util.Map;

public class CZActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    /**
     * 请输入账号
     */
    private EditText et_user_name;
    /**
     * 请输入账号密码
     */
    private EditText et_pwd;
    /**
     * 请再次输入账号密码
     */
    private EditText et_pwdag;
    /**
     * 注 册
     */
    private Button btn_reg;

    boolean iset1,iset2,iset3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_czmm);
        initView();
        getSupportActionBar().hide();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        et_user_name = (EditText) findViewById(R.id.et_user_name);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwdag = (EditText) findViewById(R.id.et_pwdag);
        btn_reg = (Button) findViewById(R.id.btn_cz);
        btn_reg.setOnClickListener(this);
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
            case R.id.btn_cz:
                String username = et_user_name.getText().toString();
                String password = et_pwd.getText().toString();
                String passwordag = et_pwdag.getText().toString();
                if(!password.equals(passwordag)){
                    Toast.makeText(this,"密码不匹配",Toast.LENGTH_SHORT).show();
                    return;
                }
                MyVolley.B.editPwd.exec(username,password).exec(this);
                break;
        }
    }

    public void editPwd(JSONObject jsonObject) {
        Map<String, Object> maps = GetDate.getEditPwd(jsonObject);
        Log.e("AAAAA", maps.get("msg").toString());
        if ((boolean) maps.get("msg")) {
            Toast.makeText(this,"修改成功",Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            Toast.makeText(this,"修改失败"+maps.get("ERROR"),Toast.LENGTH_SHORT).show();
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
                if(iset1&&iset2&&iset3){
                    btn_reg.setEnabled(true);
                }else {
                    btn_reg.setEnabled(false);
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
                if(iset1&&iset2&&iset3){
                    btn_reg.setEnabled(true);
                }else {
                    btn_reg.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_pwdag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>0){
                    iset3 = true;
                }else {
                    iset3 = false;
                }
                if(iset1&&iset2&&iset3){
                    btn_reg.setEnabled(true);
                }else {
                    btn_reg.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
