package com.example.douyin.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.douyin.App;
import com.example.douyin.R;
import com.example.douyin.adapter.MyRecyclerViewAdapter;
import com.example.douyin.adapter.PLRecyclerViewAdapter;
import com.example.douyin.util.MyVolley;
import com.example.douyin.viewpager.OnViewPagerListener;
import com.example.douyin.viewpager.ViewPagerLayoutManager;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.pojo.Pl;
import com.example.douyin.wenl.pojo.Video;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {
    EditText et_pl;
    Button btn_fb;
    String pl = "";
    private View view;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private ViewPagerLayoutManager mLayoutManager;
    private PLRecyclerViewAdapter recyclerView;
    private List<Video> list_mp4s;
    private int p  = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home,container,false);

        initView();


        initListener();

        MyVolley.B.findAllVideo.exec(App.user).exec(this);

        return view;
    }

    public void findAllVideo(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.findAllVideo(jsonObject);
        Log.e("AAAAA",maps.get("msg").toString() );
        if((boolean)maps.get("msg")){
            List<Video> lists = (List<Video>) maps.get("list_video");

                list_mp4s = lists;

            Log.e("BBB","获取视频成功" );
            Collections.shuffle(list_mp4s);
            adapter = new MyRecyclerViewAdapter(getContext(),list_mp4s);
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
                plClick(0);
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
                plClick(position);
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

    private void plClick(final int p){
        //View itemView = View.inflate(getActivity(), R.layout.item_video_page, null);
        View itemView = mRecyclerView.getChildAt(0);
        Log.e("===itemView===",itemView+"");
        Log.e("===itemView===",list_mp4s.get(p).getVideoIntro()+""+"=="+p);
        TextView tv_dz = itemView.findViewById(R.id.tv_dz);
        TextView sv_head = itemView.findViewById(R.id.si_head);
        TextView tv_pl = itemView.findViewById(R.id.tv_pl);
        tv_pl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog(p);
            }
        });
    }
    BottomSheetDialog bottomSheetDialog;
    ImageView tv_dis;
    RecyclerView recyclerViewpl ;


    private void initDialog(final int p) {
        this.p = p;
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.dialog_pl);
        //给布局设置透明背景色
        bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));
        //View view = View.inflate(getActivity(), R.layout.dialog_pl, null);
        recyclerViewpl = bottomSheetDialog.findViewById(R.id.recyclerview);
        recyclerViewpl.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView = new PLRecyclerViewAdapter(getActivity(),list_mp4s.get(p).getVideoid());

        recyclerViewpl.setAdapter(recyclerView);

        tv_dis = (ImageView)bottomSheetDialog.findViewById(R.id.dis_dia);
        et_pl = (EditText)bottomSheetDialog.findViewById(R.id.et_pl);
        btn_fb = (Button)bottomSheetDialog.findViewById(R.id.btn_fb);

        bottomSheetDialog.show();
        recyclerView.notifyDataSetChanged();
        tv_dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });



        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl=et_pl.getText().toString().trim();
                if(pl ==null || "".equals(pl)){
                    Toast.makeText(getContext(),"请输入评论内容",Toast.LENGTH_SHORT).show();
                }else {
                    MyVolley.B.addPl.exec(list_mp4s.get(p).getVideoid(),App.user,pl).exec(HomeFragment.this);
                    et_pl.setText(" ");
                }
            }
        });



        //{"videoid":"%s","userid":"%s","comment":"%s"}


    }

    public void addPl(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.addPl(jsonObject);
        if((boolean)maps.get("msg")){
            recyclerViewpl.setAdapter(new PLRecyclerViewAdapter(getActivity(),list_mp4s.get(p).getVideoid()));
            Toast.makeText(App.context,"评论成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(App.context,"评论失败",Toast.LENGTH_SHORT).show();
        }
    }

}
