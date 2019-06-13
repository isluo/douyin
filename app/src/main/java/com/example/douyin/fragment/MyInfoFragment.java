package com.example.douyin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.douyin.App;
import com.example.douyin.R;
import com.loopj.android.image.SmartImageView;

public class MyInfoFragment extends Fragment {

    private View view;
    private SmartImageView mIvHead;
    /**
     * 编辑资料
     */
    private TextView mTvEdit;
    /**
     * luo7952
     */
    private TextView mTvNname;
    /**
     * 抖音号:123123
     */
    private TextView mTvUsernaem;
    /**
     * 你还没有填写个人简介...
     */
    private TextView mTvIntroduce;
    private LinearLayout mLlZp;
    private LinearLayout mLlGz;
    private LinearLayout mLl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_myinfo, container, false);
        initView(view);
        mTvUsernaem.setText("抖音号:"+App.user);
        if(!App.nname.equals("" )|| App.nname != null){
            mTvNname.setText(App.nname);
        }
        mIvHead.setImageUrl(App.head);
        mTvIntroduce.setText(App.introduce);
        return view;
    }

    private void initView(View view) {
        mIvHead = (SmartImageView) view.findViewById(R.id.iv_head);
        mTvEdit = (TextView) view.findViewById(R.id.tv_edit);
        mTvNname = (TextView) view.findViewById(R.id.tv_nname);
        mTvUsernaem = (TextView) view.findViewById(R.id.tv_usernaem);
        mTvIntroduce = (TextView) view.findViewById(R.id.tv_introduce);
        mLlZp = (LinearLayout) view.findViewById(R.id.ll_zp);
        mLlGz = (LinearLayout) view.findViewById(R.id.ll_gz);
        mLl = (LinearLayout) view.findViewById(R.id.ll);
    }
}
