package com.example.douyin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.douyin.util.MediaUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

public class RecActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean mStartedFlag = false;
    private boolean mPlayFlag = false;
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private Camera mCamera;
    private MediaPlayer mMediaPlayer;
    private String path;
    private int timer = 0;
    private int maxSec = 10;
    private String imgPath;
    private long startTime = 0L;
    private long stopTime = 0L;
    private boolean cameraReleaseEnable = true;  //回收摄像头
    private boolean recorderReleaseEnable = false;  //回收recorder
    private boolean playerReleaseEnable = false; //回收palyer
    private SurfaceView mMSurfaceview;
    private Button mMBtnPlay;
    /**
     * 按住拍摄
     */
    private TextView mMTvRecordTip;
    private Button mMBtnRecord;
    private LinearLayout mMLlRecordBtn;
    private MaterialProgressBar mMProgress;
    private Button mMBtnCancle;
    private Button mMBtnSubmit;
    private LinearLayout mMLlRecordOp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec);
        getSupportActionBar().hide();
        initView();
        mMediaPlayer = new MediaPlayer();
        SurfaceHolder holder = mMSurfaceview.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    mSurfaceHolder = holder;
                    //使用后置摄像头
                    mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
                    mCamera.setPreviewDisplay(holder);
                    //选装90度
                    mCamera.setDisplayOrientation(90);
                    Camera.Parameters parameters = mCamera.getParameters();
                    parameters.setPictureFormat(PixelFormat.JPEG);
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦
                    mCamera.setParameters(parameters);
                } catch (IOException e) {
                    e.printStackTrace();
                    finish();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                mSurfaceHolder = holder;
                mCamera.startPreview();
                mCamera.cancelAutoFocus();
                // 关键代码 该操作必须在开启预览之后进行（最后调用），
                // 否则会黑屏，并提示该操作的下一步出错
                // 只有执行该步骤后才可以使用MediaRecorder进行录制
                // 否则会报 MediaRecorder(13280): start failed: -19
                mCamera.unlock();
                cameraReleaseEnable = true;
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                handler.removeCallbacks(runnable);
            }
        });
        mMBtnRecord.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    stopRecord();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    startRecord();
                }
                return false;
            }

        });
    }

    private void initView() {
        mMSurfaceview = (SurfaceView) findViewById(R.id.mSurfaceview);
        mMBtnPlay = (Button) findViewById(R.id.mBtnPlay);
        mMBtnPlay.setOnClickListener(this);
        mMTvRecordTip = (TextView) findViewById(R.id.mTvRecordTip);
        mMBtnRecord = (Button) findViewById(R.id.mBtnRecord);
        mMBtnRecord.setOnClickListener(this);
        mMLlRecordBtn = (LinearLayout) findViewById(R.id.mLlRecordBtn);
        mMProgress = (MaterialProgressBar) findViewById(R.id.mProgress);
        mMBtnCancle = (Button) findViewById(R.id.mBtnCancle);
        mMBtnCancle.setOnClickListener(this);
        mMBtnSubmit = (Button) findViewById(R.id.mBtnSubmit);
        mMBtnSubmit.setOnClickListener(this);
        mMLlRecordOp = (LinearLayout) findViewById(R.id.mLlRecordOp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.mBtnPlay:
                playRecord();
                break;
            case R.id.mBtnRecord:
                break;
            case R.id.mBtnCancle:
                stopPlay();
                File videoFile = new File(path);
                if (videoFile.exists() && videoFile.isFile()) {
                    videoFile.delete();
                }
                finish();
                break;
            case R.id.mBtnSubmit:
                stopPlay();
                Intent intent = new Intent(RecActivity.this,VideoUpActivity.class);
                intent.putExtra("videoPath",path);
                startActivity(intent);
                break;
        }
    }
    //开始录制
    private void startRecord() {
        Log.e("==startRecord==", "开始录制");
        if (!mStartedFlag) {
            mStartedFlag = true;
            mMLlRecordOp.setVisibility(View.INVISIBLE);
            mMBtnPlay.setVisibility(View.INVISIBLE);
            mMLlRecordBtn.setVisibility(View.VISIBLE);
            mMProgress.setVisibility(View.VISIBLE);//进度条可见
            //开始计时
            handler.postDelayed(runnable, 1000);
            recorderReleaseEnable = true;
            mRecorder = new MediaRecorder();
            mRecorder.reset();
            mRecorder.setCamera(mCamera);
            // 这两项需要放在setOutputFormat之前
            mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            // Set output file format
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            // 这两项需要放在setOutputFormat之后 IOS必须使用ACC
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            ;
            //使用MPEG_4_SP格式在华为P20 pro上停止录制时会出现
            //MediaRecorder: stop failed: -1007
            //java.lang.RuntimeException: stop failed.
            // at android.media.MediaRecorder.stop(Native Method)
            mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

            mRecorder.setVideoSize(640, 480);
            mRecorder.setVideoFrameRate(30);
            mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
            mRecorder.setOrientationHint(90);
            //设置记录会话的最大持续时间（毫秒）
            mRecorder.setMaxDuration(30 * 1000);
            path = Environment.getExternalStorageDirectory().getPath() + File.separator + "ADouVideo";
            if (path != null) {
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                path = dir.getAbsolutePath() + "/" + getDate() + ".mp4";
                mRecorder.setOutputFile(path);
                try {
                    mRecorder.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mRecorder.start();
                startTime = System.currentTimeMillis();
            }
        }
    }

    //结束录制
    private void stopRecord() {
        Toast.makeText(getApplicationContext(),"stop",Toast.LENGTH_LONG).show();
        Log.e("==stopRecord==", "结束录制");
        if (mStartedFlag) {
            mStartedFlag = false;
            mMBtnRecord.setEnabled(false);
            mMBtnRecord.setClickable(false);
            mMLlRecordBtn.setVisibility(View.INVISIBLE);
            mMProgress.setVisibility(View.INVISIBLE);
            handler.removeCallbacks(runnable);
            stopTime = System.currentTimeMillis();
            //延时确保录制时间大于1s
            if (stopTime - startTime < 1100) {
                try {
                    Thread.sleep(1100 + startTime - stopTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            mRecorder.stop();
            mRecorder.reset();
            mRecorder.release();
            recorderReleaseEnable = false;
            mCamera.lock();
            mCamera.stopPreview();
            mCamera.release();
            cameraReleaseEnable = false;
            mMBtnPlay.setVisibility(View.VISIBLE);
            mMLlRecordOp.setVisibility(View.VISIBLE);
//            MediaUtils.getImageForVideo(path, new MediaUtils.OnLoadVideoImageListener() {
//                @Override
//                public void onLoadImage(File file) {
//                    imgPath = file.getAbsolutePath();
//                }
//            });

        }
    }

    //播放录像
    private void playRecord() {
        Log.e("==playRecord==", "播放录像");
        //修复录制时home键切出再次切回时无法播放的问题
        if (cameraReleaseEnable) {
            mCamera.lock();
            mCamera.stopPreview();
            mCamera.release();
            cameraReleaseEnable = false;
        }
        playerReleaseEnable = true;
        mPlayFlag = true;
        mMBtnPlay.setVisibility(View.INVISIBLE);
        mMediaPlayer.reset();
        Uri uri = Uri.parse(path);
        mMediaPlayer = MediaPlayer.create(RecActivity.this, uri);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setDisplay(mSurfaceHolder);

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mMBtnPlay.setVisibility(View.VISIBLE);
            }
        });
        Log.e("==playRecord==", "播放录像启动");
        mMediaPlayer.start();
    }


    //停止播放录像
    private void stopPlay() {
        Log.e("==stopPlay==", "停止播放录像");
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayFlag) {
            stopPlay();
        }
        if (mStartedFlag) {
            stopRecord();
        }
        Log.e("==onStop==", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (recorderReleaseEnable) mRecorder.release();
        if (cameraReleaseEnable) {
            mCamera.stopPreview();
            mCamera.release();
        }
        if (playerReleaseEnable) {
            mMediaPlayer.release();
        }
        Log.e("==onDestroy==", "onDestroy");
    }

    public String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);       // 获取月份
        int day = ca.get(Calendar.DATE);           // 获取日
        int minute = ca.get(Calendar.MINUTE);     // 分
        int hour = ca.get(Calendar.HOUR);        // 小时
        int second = ca.get(Calendar.SECOND);      // 秒
        return "" + year + (month + 1) + day + hour + minute + second;
    }

    //用于记录视频录制时长
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timer++;
            if (timer < 100) {
                mMProgress.setProgress(timer);
                handler.postDelayed(this, 100);
            } else {
                stopRecord();
                System.currentTimeMillis();
            }
        }
    };
}
