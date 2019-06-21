package com.example.douyin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.douyin.R;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.Pl;
import com.example.douyin.wenl.pojo.User;
import com.example.douyin.wenl.pojo.Video;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class GzRecyclerViewAdapter extends RecyclerView.Adapter<GzRecyclerViewAdapter.MyViewHoder> {

    private Context context;
    private List<Video> list_mp4s;
    private int b;
    private MyViewHoder myViewHoder;
    public GzRecyclerViewAdapter(Context context, List<Video> list_mp4s) {
        this.context = context;
        this.list_mp4s = list_mp4s;


    }
    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_page,viewGroup,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder myViewHoder, final int i) {
        final Drawable topr = ContextCompat.getDrawable(context, R.mipmap.er);
        topr.setBounds(0, 0, topr.getMinimumWidth(), topr.getMinimumHeight());
        final Drawable topw = ContextCompat.getDrawable(context, R.mipmap.e);
        topw.setBounds(0, 0, topw.getMinimumWidth(), topw.getMinimumHeight());

        myViewHoder.img_thumb.setImageUrl(ImgPath.getMp4s(list_mp4s.get(i).getVideopath()));
        myViewHoder.videoView.setVideoPath(ImgPath.getMp4(list_mp4s.get(i).getVideopath()));
        myViewHoder.tv_ms.setText(list_mp4s.get(i).getVideoIntro());

        this.myViewHoder = myViewHoder;
        MyVolley.B.selectPlByVideoId.exec(list_mp4s.get(i).getVideoid()).exec(GzRecyclerViewAdapter.this);
        MyVolley.B.findVideoByVideoID.exec(list_mp4s.get(i).getVideoid()).exec(GzRecyclerViewAdapter.this);
        MyVolley.B.findUser.exec(list_mp4s.get(i).getUserid()).exec(GzRecyclerViewAdapter.this);
        if(list_mp4s.get(i).isB()){
            myViewHoder.tv_dz.setCompoundDrawables(null, topr , null, null);
        }
        b = i;
        myViewHoder.tv_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list_mp4s.get(b).isB()){
                    myViewHoder.tv_dz.setCompoundDrawables(null, topw , null, null);
                    MyVolley.B.updateVideoNum2.exec(list_mp4s.get(i).getVideoid()).exec(GzRecyclerViewAdapter.this);
                    myViewHoder.tv_dz.setText(Integer.parseInt(myViewHoder.tv_dz.getText().toString())-1+"");
                    list_mp4s.get(b).setB(false);
                }else {
                    myViewHoder.tv_dz.setCompoundDrawables(null, topr , null, null);
                    MyVolley.B.updateVideoNum.exec(list_mp4s.get(i).getVideoid()).exec(GzRecyclerViewAdapter.this);
                    myViewHoder.tv_dz.setText(Integer.parseInt(myViewHoder.tv_dz.getText().toString())+1+"");
                    list_mp4s.get(b).setB(true);
                }
            }
        });
    }

    public void findVideoByVideoID(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.findVideoByVideoID(jsonObject);
        if((boolean)maps.get("msg")){
            Video video = (Video) maps.get("video");
            myViewHoder.tv_dz.setText(video.getDz()+"");
        }else{
            myViewHoder.tv_dz.setText("0");
        }
    }


    public void updateVideoNum(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.updateVideoNum(jsonObject);
        if((boolean)maps.get("msg")){
           //+1
        }else{

        }
    }

    public void findUser(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.findUser(jsonObject);
        if((boolean)maps.get("msg")){
            User user = (User) maps.get("user");
            myViewHoder.tv_userID.setText("@"+user.getNname()+"");
            myViewHoder.sv_head.setImageUrl(ImgPath.getImg(user.getHead()));
        }else{
            myViewHoder.tv_userID.setText("@  ");
        }
    }

    public void selectPlByVideoId(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.selectPlByVideoId(jsonObject);
        if((boolean)maps.get("msg")){
            List<Pl> list_pls = (List<Pl>) maps.get("list_pl");
            myViewHoder.tv_pl.setText(list_pls.size()+"");
        }else{
            myViewHoder.tv_pl.setText(0+"");
        }
    }

    @Override
    public int getItemCount() {
        return list_mp4s.size();
    }

    public class MyViewHoder extends RecyclerView.ViewHolder {
        SmartImageView img_thumb;
        VideoView videoView;
        ImageView img_play;
        SmartImageView sv_head;
        TextView tv_ms;
        TextView tv_userID;
        TextView tv_dz;
        TextView tv_pl;

        public MyViewHoder(View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            videoView = itemView.findViewById(R.id.video_view);
            img_play = itemView.findViewById(R.id.img_play);

            tv_dz = itemView.findViewById(R.id.tv_dz);
            sv_head = itemView.findViewById(R.id.si_head);
            tv_ms = itemView.findViewById(R.id.tv_ms);
            tv_pl = itemView.findViewById(R.id.tv_pl);
            tv_userID = itemView.findViewById(R.id.tv_userID);
        }
    }
}
