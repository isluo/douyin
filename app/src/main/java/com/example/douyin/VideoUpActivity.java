package com.example.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class VideoUpActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * null
     */
    private TextView mTvPath;
    /**
     * 说点什么
     */
    private EditText mEtText;
    /**
     * 上传
     */
    private Button mBtnSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoup);
        initView();
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String path = intent.getStringExtra("videoPath");
        Log.e("==videoPath==", "videoPath=" + path);
        mTvPath.setText(path+"");
    }

    private void initView() {
        mTvPath = (TextView) findViewById(R.id.tv_path);
        mEtText = (EditText) findViewById(R.id.et_text);
        mBtnSub = (Button) findViewById(R.id.btn_sub);
        mBtnSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_sub:
                videoUp();
                break;
        }
    }

    private void videoUp() {

    }
}
