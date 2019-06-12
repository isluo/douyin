package com.example.douyin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.douyin.R;
import com.loopj.android.image.SmartImageView;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHoder> {

    private Context context;

    public MyRecyclerViewAdapter(){
    }


    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder myViewHoder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHoder extends RecyclerView.ViewHolder{
        public MyViewHoder(View itemView) {
            super(itemView);
        }
    }
}
