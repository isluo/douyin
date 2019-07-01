package com.example.douyin.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.douyin.App;
import com.example.douyin.MymsgActivity;
import com.example.douyin.R;
import com.loopj.android.image.SmartImageView;

public class MyInfoFragment extends Fragment implements View.OnClickListener {

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

    private TextView mTvZp;
    private ImageView mIvZp;

    private TextView mTvGz;
    private ImageView mIvGz;
    public static MyInfoFragment infoFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_myinfo, container, false);
        infoFragment = this;
        initView(view);
        mTvUsernaem.setText("抖音号:"+App.user);

        mIvHead.setImageUrl(App.head);

        if(App.nname.equals("null")){
            mTvNname.setText("@");
        }else{
            mTvNname.setText(App.nname);
        }

        if(App.introduce.equals("null")){
            mTvIntroduce.setText("你还没有填写个人简介...");
        }else{
            mTvIntroduce.setText(App.introduce);
        }

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
        mTvEdit.setOnClickListener(this);
        mLlZp.setOnClickListener(this);
        mLlGz.setOnClickListener(this);

        mTvZp = (TextView) view.findViewById(R.id.tv_zp);
        mIvZp = (ImageView) view.findViewById(R.id.iv_zp);
        mTvGz = (TextView) view.findViewById(R.id.tv_gz);
        mIvGz = (ImageView) view.findViewById(R.id.iv_gz);
        getChildFragmentManager().beginTransaction().replace(R.id.ll_fra, new ZPFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.tv_edit:
                startActivity(new Intent(getActivity(), MymsgActivity.class));
                break;
            case R.id.ll_zp:
                getChildFragmentManager().beginTransaction().replace(R.id.ll_fra, new ZPFragment()).commit();
                mTvGz.setTextColor(Color.parseColor("#BB9393"));
                mTvZp.setTextColor(Color.parseColor("#ffffff"));
                mIvGz.setVisibility(View.GONE);
                mIvZp.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_gz:
                getChildFragmentManager().beginTransaction().replace(R.id.ll_fra, new GZListFragment()).commit();
                mTvGz.setTextColor(Color.parseColor("#BB9393"));
                mTvZp.setTextColor(Color.parseColor("#ffffff"));
                mTvGz.setTextColor(Color.parseColor("#ffffff"));
                mTvZp.setTextColor(Color.parseColor("#BB9393"));
                mIvGz.setVisibility(View.VISIBLE);
                mIvZp.setVisibility(View.GONE);
                break;
        }
    }

    public void updata(){
        mIvHead.setImageUrl(App.head);
        if(!App.nname.equals("" )|| App.nname != null){
            mTvNname.setText(App.nname);
        }
        mIvHead.setImageUrl(App.head);
        mTvIntroduce.setText(App.introduce);
    }
}
