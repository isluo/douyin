package com.example.douyin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.douyin.MainActivity;
import com.example.douyin.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PLRecyclerViewAdapter extends  RecyclerView.Adapter<PLRecyclerViewAdapter.MyViewHolder>{

    private Context context;

    public PLRecyclerViewAdapter(Context context) {
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_pl,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_head;
        TextView tv_name;
        TextView tv_pl;
        ImageView iv_ic;
        TextView tv_dzs;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_head=(ImageView)itemView.findViewById(R.id.iv_head);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_pl=(TextView)itemView.findViewById(R.id.tv_pl);
            iv_ic=(ImageView) itemView.findViewById(R.id.iv_ic);
            tv_dzs=(TextView) itemView.findViewById(R.id.tv_dzs);

        }
    }
}