package com.example.douyin.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.douyin.R;
import com.example.douyin.widget.FullScreenVideoView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHoder> {

    private Context context;


    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_page,viewGroup,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder myViewHoder, int i) {
        //myViewHoder.videoView.setVideoURI(Uri.parse("android.resource://"+context.getPackageName()+"/"+ videos[i%2]));
        //myViewHoder.videoView.setVideoPath("http://s3.pstatp.com/aweme/resource/web/static/image/index/tvc-v2_30097df.mp4");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHoder extends RecyclerView.ViewHolder {
        ImageView img_thumb;
        VideoView videoView;
        ImageView img_play;

        public MyViewHoder(View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            videoView = itemView.findViewById(R.id.video_view);
            img_play = itemView.findViewById(R.id.img_play);
        }
    }
}
