package com.example.douyin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.example.douyin.util.MyVolley;
import com.example.douyin.widget.MySmartImage;

import java.util.ArrayList;
import java.util.List;

public class MymsgActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * TextView
     */
    private TextView mTvTitle;
    private ImageView mBack;
    /**
     * 编辑
     */
    private TextView mTvBj;
    private MySmartImage mIvHead;
    /**
     * 昵称
     */
    private EditText mEtNc;
    /**
     * 2134433423
     */
    private TextView mTvDyh;
    /**
     * jianjei
     */
    private EditText mEtJj;
    /**
     * 男
     */
    private TextView mTvXb;
    private ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymsg);
        initView();
        getSupportActionBar().hide();
    }

    private void initView() {
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTvBj = (TextView) findViewById(R.id.tv_bj);
        mTvBj.setOnClickListener(this);
        mIvHead = (MySmartImage) findViewById(R.id.iv_head);
        mIvHead.setOnClickListener(this);
        mEtNc = (EditText) findViewById(R.id.et_nc);
        mTvDyh = (TextView) findViewById(R.id.tv_dyh);
        mTvDyh.setOnClickListener(this);
        mEtJj = (EditText) findViewById(R.id.et_jj);
        mTvXb = (TextView) findViewById(R.id.tv_xb);
        mTvXb.setOnClickListener(this);
        mTvTitle.setText("个人信息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.tv_bj:
                if (mTvBj.getText().toString().equals("编辑")){
                    mTvBj.setText("保存");
                    mEtNc.setEnabled(true);
                    mEtJj.setEnabled(true);
                }else {
                    mTvBj.setText("编辑");
                    mEtNc.setEnabled(false);
                    mEtJj.setEnabled(false);
                }

                break;
            case R.id.iv_head:
                break;
            case R.id.tv_dyh:
                break;
            case R.id.tv_xb:
                selectSex();
                break;
        }
    }

    public void selectSex() {
        list.add("男");
        list.add("女");
        OptionsPickerView<String> pickerView = new OptionsPickerView<>(MymsgActivity.this);
        pickerView.setPicker(list);
        // 设置点击外部是否消失
        pickerView.setCancelable(true);
        // 设置标题
        pickerView.setTitle("性别");
        pickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                String sex = list.get(option1);

                Toast.makeText(MymsgActivity.this, sex+"", Toast.LENGTH_SHORT).show();
            }
        });
        pickerView.show();
    }
}
