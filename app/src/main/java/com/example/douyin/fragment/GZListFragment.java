package com.example.douyin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.douyin.App;
import com.example.douyin.R;
import com.example.douyin.adapter.MyRecyclerViewAdapter2;
import com.example.douyin.adapter.PLRecyclerViewAdapter2;
import com.example.douyin.util.MyVolley;
import com.example.douyin.wenl.GetDate;
import com.example.douyin.wenl.ImgPath;
import com.example.douyin.wenl.pojo.User;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GZListFragment extends Fragment {
    private View view;
    private RecyclerView mRecyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gz, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyVolley.B.findByUser.exec(App.user).exec(GZListFragment.this);

    }


    List<Integer> list_user2 = new ArrayList<>();
    public void findByUser(JSONObject jsonObject){

        Map<String,Object> maps = GetDate.findByUser(jsonObject);
        if((boolean)maps.get("msg")){
            List<User> list_user = (List<User>) maps.get("list_user");
            list_user2 = (List<Integer>) maps.get("inte");
            for (User user : list_user) {
                MyVolley.B.findUser.exec(user.getUsername()).exec(GZListFragment.this);
            }
        }else{

        }
    }
    List<User> users = new ArrayList<>();

    public void findUser(JSONObject jsonObject){
        Map<String,Object> maps = GetDate.findUser(jsonObject);
        if((boolean)maps.get("msg")){
            User user = (User) maps.get("user");
            users.add(user);

            if(list_user2.size() == users.size()){
                PLRecyclerViewAdapter2 adapter2 = new PLRecyclerViewAdapter2(getContext(),users,list_user2);
                mRecyclerview.setAdapter(adapter2);
            }
        }
    }
}