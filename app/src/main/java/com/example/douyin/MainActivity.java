package com.example.douyin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.douyin.fragment.GzFragment;
import com.example.douyin.fragment.HomeFragment;
import com.example.douyin.fragment.MyInfoFragment;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.pojo.User;
import com.example.douyin.wenl.pojo.Video;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout relativelayout;
    /**
     * 首页
     */
    private TextView tv_home;
    private ImageView iv_home;
    /**
     * 关注
     */
    private TextView tv_follow;
    private ImageView iv_follow;
    private ImageView iv_shoot;
    /**
     * 消息
     */
    private TextView tv_msg;
    private ImageView iv_msg;
    /**
     * 我
     */
    private TextView tv_my;
    private ImageView iv_my;

    private TextView[] textViews;
    private ImageView[] imageViews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getSupportActionBar().hide();
        getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout,new HomeFragment()).commit();
    }

    private void initView() {
        relativelayout = (RelativeLayout) findViewById(R.id.relativelayout);
        tv_home = (TextView) findViewById(R.id.tv_home);
        tv_home.setOnClickListener(this);
        iv_home = (ImageView) findViewById(R.id.iv_home);
        tv_follow = (TextView) findViewById(R.id.tv_follow);
        tv_follow.setOnClickListener(this);
        iv_follow = (ImageView) findViewById(R.id.iv_follow);
        iv_shoot = (ImageView) findViewById(R.id.iv_shoot);
        iv_shoot.setOnClickListener(this);
        tv_msg = (TextView) findViewById(R.id.tv_msg);
        iv_msg = (ImageView) findViewById(R.id.iv_msg);
        tv_msg.setOnClickListener(this);
        tv_my = (TextView) findViewById(R.id.tv_my);
        tv_my.setOnClickListener(this);
        iv_my = (ImageView) findViewById(R.id.iv_my);
        textViews = new TextView[]{tv_home,tv_follow,tv_msg,tv_my};
        imageViews = new ImageView[]{iv_home,iv_follow,iv_msg,iv_my};
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout,new HomeFragment()).commit();
                bottomChange(0);
                break;
            case R.id.tv_follow:
                getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout,new GzFragment()).commit();
                bottomChange(1);
                break;
            case R.id.iv_shoot:
                startActivity(new Intent(MainActivity.this,RecActivity.class));
                break;
            case R.id.tv_msg:
                bottomChange(2);

                break;
            case R.id.tv_my:getSupportFragmentManager().beginTransaction().replace(R.id.relativelayout,new MyInfoFragment()).commit();
                bottomChange(3);
                break;
        }
    }



    public void bottomChange(int p){
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setBackgroundResource(R.drawable.linen);
            textViews[i].setTextColor(Color.parseColor("#55c7bb"));
        }
        imageViews[p].setBackgroundResource(R.drawable.linew);
        textViews[p].setTextColor(Color.parseColor("#ffffff"));
    }


}
