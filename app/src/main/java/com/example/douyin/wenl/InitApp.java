package com.example.douyin.wenl;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.douyin.App;
import com.example.douyin.LoginActivity;
import com.example.douyin.MainActivity;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.pojo.User;

import org.json.JSONObject;

import java.util.Map;

public class InitApp {
    public InitApp(){
        MyVolley.B.login.exec(App.user,App.password).exec(this);
    }


    public void login(JSONObject jsonObject){

        Map<String, Object> maps = GetDate.getLogin(jsonObject);
        Log.e("AAAAA", maps.get("msg").toString());
        if ((boolean) maps.get("msg")) {
            User user = (User) maps.get("user");
            App.user = user.getUsername();
            App.head = ImgPath.getImg(user.getHead());
            App.nname = user.getNname();
            App.sex = user.getGender();
            App.introduce = user.getIntroduce();
        } else {
            Log.e("CCC", "登录失败" + maps.get("ERROR").toString());
        }
    }
}
