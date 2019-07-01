package com.example.douyin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.douyin.App;
import com.example.douyin.R;
import com.example.douyin.adapter.GzRecyclerViewAdapter;
import com.example.douyin.adapter.MyRecyclerViewAdapter2;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.pojo.Video;

import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ZPFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerview;
    List<Video> list_mp4s;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zp, container, false);
        initView(view);
        MyVolley.B.findVideoByUserID.exec(App.user).exec(this);
        return view;
    }

    private void initView(View view) {
        mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerview.setLayoutManager(gridLayoutManager);

    }


    public void findVideoByUserID(JSONObject jsonObject){
        Log.e("aaaaaaaaa",jsonObject.toString() );
        Map<String,Object> maps = GetDate.findVideoByUserID(jsonObject);
        Log.e("AAAAA",maps.get("msg").toString() );
        if((boolean)maps.get("msg")){
            List<Video> lists = (List<Video>) maps.get("list_video");
            list_mp4s = lists;
            MyRecyclerViewAdapter2 myRecyclerViewAdapter2 = new MyRecyclerViewAdapter2(getContext(),list_mp4s);
            mRecyclerview.setAdapter(myRecyclerViewAdapter2);
            Log.e("BBB","获取视频成功" );
        }else{
            Log.e("CCC","修改信息失败"+maps.get("ERROR").toString() );
        }
    }
}
