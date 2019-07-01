package com.example.douyin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.douyin.App;
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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHoder> {

    private Context context;
    private List<Video> list_mp4s;
    private int b;
    private MyViewHoder myViewHoder;
    public MyRecyclerViewAdapter(Context context, List<Video> list_mp4s) {
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

        myViewHoder.sv_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(""+App.user,"AAA"+list_mp4s.get(i).getUserid());
                myViewHoder.imageView.setBackgroundResource(R.drawable.gz1);
                MyVolley.B.addGz.exec(App.user,list_mp4s.get(i).getUserid()).exec(MyRecyclerViewAdapter.this);
            }
        });

        this.myViewHoder = myViewHoder;
        MyVolley.B.selectPlByVideoId.exec(list_mp4s.get(i).getVideoid()).exec(MyRecyclerViewAdapter.this);
        MyVolley.B.findVideoByVideoID.exec(list_mp4s.get(i).getVideoid()).exec(MyRecyclerViewAdapter.this);
        MyVolley.B.findUser.exec(list_mp4s.get(i).getUserid()).exec(MyRecyclerViewAdapter.this);
        if(list_mp4s.get(i).isB()){
            myViewHoder.tv_dz.setCompoundDrawables(null, topr , null, null);
        }
        b = i;
        myViewHoder.tv_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list_mp4s.get(b).isB()){
                    myViewHoder.tv_dz.setCompoundDrawables(null, topw , null, null);
                    MyVolley.B.updateVideoNum2.exec(list_mp4s.get(i).getVideoid()).exec(MyRecyclerViewAdapter.this);
                    myViewHoder.tv_dz.setText(Integer.parseInt(myViewHoder.tv_dz.getText().toString())-1+"");
                    list_mp4s.get(b).setB(false);
                }else {
                    myViewHoder.tv_dz.setCompoundDrawables(null, topr , null, null);
                    MyVolley.B.updateVideoNum.exec(list_mp4s.get(i).getVideoid()).exec(MyRecyclerViewAdapter.this);
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

    public void addGz(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.addGz(jsonObject);
        if((boolean)maps.get("msg")){
            Toast.makeText(App.context,"关注成功",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(App.context,"已经关注",Toast.LENGTH_SHORT).show();
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
            if(user.getNname() == null || user.getNname().equals("")||user.getNname().equals("null")){
                myViewHoder.tv_userID.setText("@");
            }
            myViewHoder.sv_head.setImageUrl(ImgPath.getImg(user.getHead()));
        }else{
            myViewHoder.tv_userID.setText("@");
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
        ImageView imageView;

        public MyViewHoder(View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            videoView = itemView.findViewById(R.id.video_view);
            img_play = itemView.findViewById(R.id.img_play);

            imageView = itemView.findViewById(R.id.iv_gz);
            tv_dz = itemView.findViewById(R.id.tv_dz);
            sv_head = itemView.findViewById(R.id.si_head);
            tv_ms = itemView.findViewById(R.id.tv_ms);
            tv_pl = itemView.findViewById(R.id.tv_pl);
            tv_userID = itemView.findViewById(R.id.tv_userID);
        }
    }
}
