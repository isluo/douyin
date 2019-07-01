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

import com.example.douyin.MainActivity;
import com.example.douyin.R;
import com.example.douyin.fragment.MyInfoFragment;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.Pl;
import com.example.douyin.wenl.pojo.User;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PLRecyclerViewAdapter extends  RecyclerView.Adapter<PLRecyclerViewAdapter.MyViewHolder>{

    private Context context;
    private long videoid;
    private List<Pl> list_pl;
    private MyViewHolder myViewHolder;
    private int b;
    public PLRecyclerViewAdapter(Context context,long videoid) {
        this.context = context;
        this.videoid = videoid;
        list_pl = new ArrayList<>();
        MyVolley.B.selectPlByVideoId.exec(videoid).exec(PLRecyclerViewAdapter.this);
    }

    public void selectPlByVideoId(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.selectPlByVideoId(jsonObject);
        if((boolean)maps.get("msg")){
            list_pl = (List<Pl>) maps.get("list_pl");
        }else{
            Log.e("selectPlByVideoId", "没有评论");
        }
    }

    public void findUser(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.findUser(jsonObject);
        if((boolean)maps.get("msg")){
            User user = (User) maps.get("user");
            myViewHolder.iv_head.setImageUrl(ImgPath.getImg(user.getHead())+"");
            if(user.getNname() == null || user.getNname().equals("")||user.getNname().equals("null")){
                myViewHolder.tv_name.setText("@");
            }else{
                myViewHolder.tv_name.setText("@"+user.getNname());
            }
        }else{
            myViewHolder.tv_name.setText("@");
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_pl,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        this.myViewHolder = myViewHolder;
        myViewHolder.tv_pl.setText(list_pl.get(i).getComment()+"");
        myViewHolder.tv_dzs.setText(list_pl.get(i).getDz()+"");

        myViewHolder.iv_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list_pl.get(b).isB()){
                    myViewHolder.iv_ic.setImageResource(R.drawable.eh);
                    MyVolley.B.updatePlNum2.exec(list_pl.get(i).getId()).exec(PLRecyclerViewAdapter.this);
                    myViewHolder.tv_dzs.setText(Integer.parseInt(myViewHolder.tv_dzs.getText().toString())-1+"");
                    list_pl.get(b).setB(false);
                }else {
                    myViewHolder.iv_ic.setImageResource(R.drawable.er);
                    MyVolley.B.updatePlNum.exec(list_pl.get(i).getId()).exec(PLRecyclerViewAdapter.this);
                    myViewHolder.tv_dzs.setText(Integer.parseInt(myViewHolder.tv_dzs.getText().toString())+1+"");
                    list_pl.get(b).setB(true);
                }
            }
        });
        MyVolley.B.findUser.exec(list_pl.get(i).getUserid()).exec(PLRecyclerViewAdapter.this);



    }

    @Override
    public int getItemCount() {
        return list_pl.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        SmartImageView iv_head;
        TextView tv_name;
        TextView tv_pl;
        ImageView iv_ic;
        TextView tv_dzs;
        SmartImageView si_head;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_head=(SmartImageView)itemView.findViewById(R.id.iv_head);
            tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            tv_pl=(TextView)itemView.findViewById(R.id.tv_pl);
            iv_ic=(ImageView) itemView.findViewById(R.id.iv_ic);
            tv_dzs=(TextView) itemView.findViewById(R.id.tv_dzs);

        }
    }
}