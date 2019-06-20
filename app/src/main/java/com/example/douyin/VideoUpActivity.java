package com.example.douyin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.douyin.dialog.LoadingDialog;
import com.example.douyin.util.PostUploadRequest;
import com.example.douyin.wenl.pojo.Video;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VideoUpActivity extends AppCompatActivity implements View.OnClickListener {


    /**
     * 说点什么
     */
    private EditText mEtText;
    /**
     * 上传
     */
    private Button mBtnSub;

    private LoadingDialog mLoadingDialog;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoup);
        initView();
        getSupportActionBar().hide();
        Intent intent = getIntent();
        path = intent.getStringExtra("videoPath");
        Log.e("==videoPath==", "videoPath=" + path);

    }

    private void initView() {
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
                if (mLoadingDialog == null) {
                    mLoadingDialog = new LoadingDialog(this);
                }
                mLoadingDialog.show();
                videoUp();

                break;
        }
    }

    private void videoUp() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            String requestURL = "http://"+App.ip+"/wla/dyin/addVideo";

            File file = new File(path);

            Map<String, String[]> fileMap = new HashMap<>();

            fileMap.put("file", new String[]{path, file.getName()});

            Log.e("SSSSSS", "" + fileMap.size());

            Map<String, String> mapText = new HashMap<>();
            mapText.put("userID", App.user);
            String jj = mEtText.getText().toString().trim();

            mapText.put("videoIntro",jj);

            PostUploadRequest postUploadRequest = new PostUploadRequest(requestURL, fileMap, mapText, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
                        mLoadingDialog.dismiss();
                    }
                    Toast.makeText(App.context,"上传成功",Toast.LENGTH_SHORT).show();
                    VideoUpActivity.this.finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    if(mLoadingDialog != null && mLoadingDialog.isShowing()) {
                        mLoadingDialog.dismiss();
                    }
                    Toast.makeText(App.context,"上传失败",Toast.LENGTH_SHORT).show();
                    VideoUpActivity.this.finish();
                }
            });

            RequestQueue requset = Volley.newRequestQueue(getApplicationContext());
            requset.add(postUploadRequest);

        }
    }

    //初始化
    //
    //关闭
    //i
    //
    //
    //开启
    //
    //
    //
    //
}
