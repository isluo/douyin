package com.example.douyin.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.douyin.App;
import com.example.douyin.R;
import com.example.douyin.adapter.GzRecyclerViewAdapter;
import com.example.douyin.adapter.MyRecyclerViewAdapter;
import com.example.douyin.util.MyVolley;
import com.example.douyin.viewpager.OnViewPagerListener;
import com.example.douyin.viewpager.ViewPagerLayoutManager;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.pojo.Video;


import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GzFragment extends Fragment {

    private View view;
    private RecyclerView mRecyclerView;
    private GzRecyclerViewAdapter adapter;
    private ViewPagerLayoutManager mLayoutManager;
    private List<Video> list_mp4s;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

        initView();


        initListener();

        MyVolley.B.findVideoByGzUserID.exec(App.user).exec(this);

        return view;
    }

    public void findVideoByGzUserID(JSONObject jsonObject){
        Log.e("aaaaaaaaa",jsonObject.toString() );
        Map<String,Object> maps = GetDate.findVideoByGzUserID(jsonObject);
        Log.e("AAAAA",maps.get("msg").toString() );
        if((boolean)maps.get("msg")){
            List<Video> lists = (List<Video>) maps.get("list_video");
                list_mp4s = lists;
            Log.e("BBB","获取视频成功" );
            Collections.shuffle(list_mp4s);
            adapter = new GzRecyclerViewAdapter(getContext(),list_mp4s);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(adapter);


            
        }else{
            Log.e("CCC","修改信息失败"+maps.get("ERROR").toString() );
        }
    }



    private void initView() {
        mRecyclerView = view.findViewById(R.id.recyclerview);

        mLayoutManager = new ViewPagerLayoutManager(getContext(), OrientationHelper.VERTICAL);


    }

    private void initListener(){
        mLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {

            @Override
            public void onInitComplete() {
                playVideo(0);
            }

            @Override
            public void onPageRelease(boolean isNext,int position) {
                int index = 0;
                if (isNext){
                    index = 0;
                }else {
                    index = 1;
                }
                releaseVideo(index);
            }

            @Override
            public void onPageSelected(int position,boolean isBottom) {
                playVideo(0);
            }


        });
    }

    private void playVideo(int position) {
        View itemView = mRecyclerView.getChildAt(0);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final MediaPlayer[] mediaPlayer = new MediaPlayer[1];
        videoView.start();
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                mediaPlayer[0] = mp;
                mp.setLooping(true);
                imgThumb.animate().alpha(0).setDuration(200).start();
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });


        imgPlay.setOnClickListener(new View.OnClickListener() {
            boolean isPlaying = true;
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()){
                    imgPlay.animate().alpha(1f).start();
                    videoView.pause();
                    isPlaying = false;
                }else {
                    imgPlay.animate().alpha(0f).start();
                    videoView.start();
                    isPlaying = true;
                }
            }
        });
    }

    private void releaseVideo(int index){
        View itemView = mRecyclerView.getChildAt(index);
        final VideoView videoView = itemView.findViewById(R.id.video_view);
        //final ImageView imgThumb = itemView.findViewById(R.id.img_thumb);
        final ImageView imgPlay = itemView.findViewById(R.id.img_play);
        videoView.stopPlayback();
        //imgThumb.animate().alpha(1).start();
        imgPlay.animate().alpha(0f).start();
    }
}
