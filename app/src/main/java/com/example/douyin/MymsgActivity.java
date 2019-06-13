package com.example.douyin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.douyin.dialog.HeadDialog;
import com.example.douyin.util.MyVolley;
import com.example.douyin.util.PostUploadRequest;
import com.example.douyin.widget.MySmartImage;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //相册请求码
    private static final int ALBUM_REQUEST_CODE = 1;
    //相机请求码
    private static final int CAMERA_REQUEST_CODE = 2;
    //剪裁请求码
    private static final int CROP_REQUEST_CODE = 3;
    //调用照相机返回图片文件
    private File tempFile;

    public static MymsgActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymsg);
        instance = this;
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
                    mTvXb.setEnabled(true);
                    mTvDyh.setEnabled(true);
                    mEtNc.setTextColor(Color.parseColor("#ffffff"));
                    mEtJj.setTextColor(Color.parseColor("#ffffff"));
                    mTvXb.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    mTvBj.setText("编辑");
                    mEtNc.setEnabled(false);
                    mEtJj.setEnabled(false);
                    mTvXb.setEnabled(false);
                    mTvDyh.setEnabled(false);
                    mEtNc.setTextColor(Color.parseColor("#BE9191"));
                    mEtJj.setTextColor(Color.parseColor("#BE9191"));
                    mTvXb.setTextColor(Color.parseColor("#BE9191"));
                    //执行保存上传方法
                }
                break;
            case R.id.iv_head:
                HeadDialog dialog = new HeadDialog(MymsgActivity.this);
                //dialog.setTitle("修改头像");
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.show();
                break;
            case R.id.tv_dyh:
                Toast.makeText(MymsgActivity.this,"抖音号不可更改", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_xb:
                selectSex();
                break;
        }
    }

    /**
     * 从相机获取图片
     */
    public void getPicFromCamera() {
        //用于保存调用相机拍照后所生成的文件
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(), System.currentTimeMillis() + ".jpg");
        Log.e("===file===", tempFile.getPath());
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {   //如果在Android7.0以上,使用FileProvider获取Uri
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(MymsgActivity.this, "com.example.douyin.fileprovider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
            Log.e("===contentUri===", contentUri.toString());
        } else {    //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    /**
     * 从相册获取图片
     */
    public void getPicFromAlbm() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, ALBUM_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        switch (requestCode) {
            case CAMERA_REQUEST_CODE:   //调用相机后返回
                if (resultCode == RESULT_OK) {
                    //用相机返回的照片去调用剪裁也需要对Uri进行处理
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(MymsgActivity.this, "com.example.douyin.fileprovider", tempFile);
                        cropPhoto(contentUri);
                        Log.e("===request=contentUri",""+contentUri.toString());
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            case ALBUM_REQUEST_CODE:    //调用相册后返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CROP_REQUEST_CODE:     //调用剪裁后返回
                Bundle bundle = null;
                if (intent != null) {
                    bundle = intent.getExtras();
                }
                if (bundle != null) {
                    //在这里获得了剪裁后的Bitmap对象，可以用于上传
                    Bitmap image = bundle.getParcelable("data");
                    //设置到ImageView上
                    mIvHead.setImageBitmap(image);
                    //也可以进行一些保存、压缩等操作后上传
                    String path = saveImage("saveImage" + System.currentTimeMillis(), image);
                    Log.e("===ImagePath===", "" + path);

                    String state = Environment.getExternalStorageState();

//                    if (state.equals(Environment.MEDIA_MOUNTED)) {
//
//                        String requestURL = "http://"+App.ip+"/student/lostfound/upload";
//
//                        File file = new File(path);
//
//                        Map<String, String[]> fileMap = new HashMap<>();
//
//                        fileMap.put("file", new String[]{path, file.getName()});
//
//                        Log.e("SSSSSS", "" + fileMap.size());
//
//                        Map<String, String> mapText = new HashMap<>();
//                        mapText.put("username", App.user);
//
//                        PostUploadRequest postUploadRequest = new PostUploadRequest(requestURL, fileMap, mapText, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject jsonObject) {
//                                Log.e("BBBBBBB", "" + jsonObject.toString());
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError volleyError) {
//                                Log.e("CCCCC", "" + volleyError.toString());
//                            }
//                        });
//
//                        RequestQueue requset = Volley.newRequestQueue(getApplicationContext());
//                        requset.add(postUploadRequest);
//
//                    }

                }
                break;
        }
    }


    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        //读写权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        /** 否则超过binder机制的缓存大小的1M限制
         * 报TransactionTooLargeException*/
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CROP_REQUEST_CODE);
    }


    public String saveImage(String name, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory().getPath());
        // String path = saveImage("saveImage" + System.currentTimeMillis(), image);
//        if (!appDir.exists()) {
//            appDir.mkdir();
//        }
        File filee = new File(appDir, "aaaa");
        if (!filee.exists()) {
            filee.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(filee, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return file.getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
                mTvXb.setText(sex);
                //Toast.makeText(MymsgActivity.this, sex+"", Toast.LENGTH_SHORT).show();
            }
        });
        pickerView.show();
    }
}
