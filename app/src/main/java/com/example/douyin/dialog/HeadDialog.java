package com.example.douyin.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.douyin.MymsgActivity;
import com.example.douyin.R;

public class HeadDialog extends Dialog {

    private TextView tv1,tv2,tv3;

    public HeadDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_head);
        tv1 = (TextView)findViewById(R.id.tv_pai);
        tv2 = (TextView)findViewById(R.id.tv_xc);
        tv3 = (TextView)findViewById(R.id.tv_dm);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MymsgActivity.instance. getPicFromCamera();
                dismiss();
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MymsgActivity.instance. getPicFromAlbm();
                dismiss();
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
