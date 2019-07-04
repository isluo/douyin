package com.example.douyin;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.MediaController;

import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.Video;
import com.example.douyin.widget.FullScreenVideoView;
import com.example.douyin.widget.MySmartImage;
import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;

public class ZPActivity extends AppCompatActivity {

    private FullScreenVideoView mVideo;
    private Video video;
    private SmartImageView mImgThumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zp);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        String JsonData = intent.getStringExtra("mp4path");
        video = new Gson().fromJson(JsonData, Video.class);
        Log.e("===mp", ImgPath.getMp4(video.getVideopath()) + "===\n"+ImgPath.getMp4s(video.getVideopath()));
        initView();
    }

    private void initView() {
        mVideo = (FullScreenVideoView) findViewById(R.id.video);
        mImgThumb = (SmartImageView) findViewById(R.id.img_thumb);
        mImgThumb.setImageUrl(ImgPath.getMp4s(video.getVideopath()));
        MediaController controller = new MediaController(this);
        mVideo.setMediaController(controller);
        mVideo.setVideoPath(ImgPath.getMp4(video.getVideopath()));
        //mVideo.setVideoPath("https://boot-video.xuexi.cn/video/1004/p/d7fc75469fa33bd31c251ee9277aa7e0-60a4c4b519844495979f45882bc4c00c-2.mp4");
        mVideo.requestFocus();
        mVideo.start();
        mVideo.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mImgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });


    }
}
