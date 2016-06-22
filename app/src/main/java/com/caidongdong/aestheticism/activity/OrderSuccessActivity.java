package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import com.caidongdong.aestheticism.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderSuccessActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.ibtn_back_to_main)
    ImageButton ibtnBackToMain;
    @Bind(R.id.btn_check_order_status)
    Button btnCheckOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_success);
        ButterKnife.bind(this);
        initListener();
    }

    private void initView() {

    }

    private void initListener() {
        ibtnBackToMain.setOnClickListener(this);
        btnCheckOrderStatus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_back_to_main:
                finish();
                break;
            case R.id.btn_check_order_status:
                Intent orderStatus = new Intent(this,OrderDetailActivity.class);
                startActivity(orderStatus);
                break;
        }
    }
}
