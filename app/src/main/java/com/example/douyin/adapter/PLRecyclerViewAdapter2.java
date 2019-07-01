package com.example.douyin.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.douyin.App;
import com.example.douyin.R;
import com.example.douyin.fragment.GZListFragment;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.Pl;
import com.example.douyin.wenl.pojo.User;
import com.example.douyin.widget.MySmartImage;
import com.loopj.android.image.SmartImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PLRecyclerViewAdapter2 extends  RecyclerView.Adapter<PLRecyclerViewAdapter2.MyViewHolder>{

    private Context context;


    private MyViewHolder myViewHolder;
    private List<User> userList = new ArrayList<>();
    private List<Integer> userList2 = new ArrayList<>();
    int i1 = 0;
    private boolean falg = false;
    public PLRecyclerViewAdapter2(Context context,List<User> userList,List<Integer> userList2) {
        this.context = context;
            this.userList = userList;
        this.userList2 = userList2;
        Log.e("AAAA",userList.get(0).toString() );
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_gz,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        this.myViewHolder = myViewHolder;
        myViewHolder.smartImage.setImageUrl(ImgPath.getImg(userList.get(i).getHead()));

        if(userList.get(i).getIntroduce().equals("null")){
            myViewHolder.ms.setText("该用户暂无评论");
        }else{
            myViewHolder.ms.setText(userList.get(i).getIntroduce());
        }
        if(userList.get(i).getNname().equals("null")){
            myViewHolder.tv_name.setText("@ ");
        }else{
            myViewHolder.tv_name.setText(userList.get(i).getNname());
        }

        myViewHolder.bt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //取消关注
                    MyVolley.B.removeGz.exec(userList2.get(i)).exec(PLRecyclerViewAdapter2.this);
                userList.remove(i1);
                Log.e("AAAA",""+i );
                notifyItemRemoved(i);
                notifyDataSetChanged();
            }
        });

    }


    public void removeGz(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.removeGz(jsonObject);
        if((boolean)maps.get("msg")){

        }else{
            Toast.makeText(App.context,"取消关注失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        MySmartImage smartImage;
        TextView tv_name;
        TextView ms;
        Button bt_1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            smartImage = itemView.findViewById(R.id.iv_head);
            tv_name = itemView.findViewById(R.id.tv_name);
            ms = itemView.findViewById(R.id.tv_pl);
            bt_1 = itemView.findViewById(R.id.bt_1);
        }
    }
}