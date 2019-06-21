package com.example.douyin.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.douyin.App;
import com.example.douyin.R;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.Pl;
import com.example.douyin.wenl.pojo.Video;
import com.example.douyin.wenl.pojo.Videos;
import com.example.douyin.widget.FullScreenVideoView;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHoder> {

    private Context context;
    private List<Videos> list_mp4s;
    private MyViewHoder myViewHoder;
    public MyRecyclerViewAdapter(Context context, List<Videos> list_mp4s) {
        this.context = context;
        this.list_mp4s = list_mp4s;
    }
    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_page,viewGroup,false);
        return new MyViewHoder(view);
    }
    int b;
    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder myViewHoder, int i) {
        final Drawable topr = ContextCompat.getDrawable(context, R.mipmap.er);
        topr.setBounds(0, 0, topr.getMinimumWidth(), topr.getMinimumHeight());
        final Drawable topw = ContextCompat.getDrawable(context, R.mipmap.e);
        topw.setBounds(0, 0, topw.getMinimumWidth(), topw.getMinimumHeight());
        //myViewHoder.videoView.setVideoURI(Uri.parse("android.resource://"+context.getPackageName()+"/"+ videos[i%2]));
        myViewHoder.img_thumb.setImageUrl(ImgPath.getMp4s(list_mp4s.get(i).getVideopath()));
        //myViewHoder.videoView.setVideoPath(ImgPath.getMp4(list_mp4s.get(i).getVideopath()));

        myViewHoder.tv_userID.setText("@"+list_mp4s.get(i).getUserid());
        myViewHoder.tv_ms.setText(list_mp4s.get(i).getVideoIntro());
        myViewHoder.sv_head.setImageUrl(list_mp4s.get(i).getHead());
        myViewHoder.tv_dz.setText(list_mp4s.get(i).getDz()+"");

        if(list_mp4s.get(i).isB()){
            myViewHoder.tv_dz.setCompoundDrawables(null, topr , null, null);
        }
         b = i;
        myViewHoder.tv_dz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_mp4s.get(b).isB()){
                    myViewHoder.tv_dz.setCompoundDrawables(null, topw , null, null);
                    list_mp4s.get(b).setB(false);
                }else {
                    myViewHoder.tv_dz.setCompoundDrawables(null, topr , null, null);
                    list_mp4s.get(b).setB(true);
                }

            }
        });

       /* this.myViewHoder = myViewHoder;
        MyVolley.B.selectPlByVideoId.exec(list_mp4s.get(i).getVideoid()).exec(MyRecyclerViewAdapter.this);*/

        Log.e("AAAAAA"+ImgPath.getMp4(list_mp4s.get(i).getVideopath()),"BBB"+ ImgPath.getMp4(list_mp4s.get(i).getVideopath()));
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
