package com.example.douyin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.douyin.R;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.Video;
import com.loopj.android.image.SmartImageView;

import java.util.List;

public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyRecyclerViewAdapter2.MyViewHoder> {
    private Context context;
    //数据
    private List<Video> list;

    private OnRecyclerItemClickListener mOnItemClickListener;

    public MyRecyclerViewAdapter2(Context context, List<Video> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zp, viewGroup,false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHoder myViewHoder, int i) {

        myViewHoder.imageView.setImageUrl(ImgPath.getMp4s(list.get(i).getVideopath()));
        myViewHoder.tv_cate.setText(list.get(i).getVideoIntro());
        Log.e("===onBindViewHolder","");
        if(mOnItemClickListener !=null){
            myViewHoder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里是为textView设置了单击事件,回调出去
                    //mOnItemClickListener.onItemClick(v,position);这里需要获取布局中的position,不然乱序
                    mOnItemClickListener.onItemClick(v,myViewHoder.getLayoutPosition());
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHoder extends RecyclerView.ViewHolder{
        SmartImageView imageView;
        TextView tv_cate;
        public MyViewHoder(View itemView) {
            super(itemView);
            imageView = (SmartImageView)itemView.findViewById(R.id.iv_zpt);
            tv_cate = (TextView) itemView.findViewById(R.id.tv_zp);
        }
    }

    public interface OnRecyclerItemClickListener{
        public void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnRecyclerItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

}
