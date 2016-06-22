package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.caidongdong.aestheticism.view.ToastView;
import com.github.channguyen.rsv.RangeSliderView;
import com.squareup.okhttp.Request;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends Activity {

    @Bind(R.id.imbtn_back)
    ImageButton imbtnBack;
    @Bind(R.id.rsv_small)
    RangeSliderView rsvSmall;
    @Bind(R.id.personinfo_btn_pwd_confirm)
    Button personinfoBtnPwdConfirm;
    @Bind(R.id.edit_txt_old_pwd)
    EditText editTxtOldPwd;
    @Bind(R.id.edit_txt_new_pwd)
    EditText editTxtNewPwd;
    @Bind(R.id.edit_txt_renew_pwd)
    EditText editTxtRenewPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        rsvSmall.setOnSlideListener(new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                return;
            }
        });
        rsvSmall.setInitialIndex(3);
        rsvSmall.setFocusable(false);
        imbtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyPasswordActivity.this.finish();
            }
        });
        personinfoBtnPwdConfirm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (editTxtOldPwd.getText().toString().isEmpty()) {
                    showMsg("请输入旧密码");
                    return;
                }
                if (editTxtNewPwd.getText().toString().isEmpty()) {
                    showMsg("请输入新密码");
                    return;
                }
                if (editTxtRenewPwd.getText().toString().isEmpty()) {
                    showMsg("请再输入新密码");
                    return;
                }
                if (!editTxtNewPwd.getText().toString().equals(editTxtRenewPwd.getText().toString())) {
                    showMsg("两次新密码不匹配");
                }else {
                    if (editTxtNewPwd.getText().toString().equals(editTxtOldPwd.getText().toString())) {
                        showMsg("旧密码与新密码相同，请重新设置新密码");
                    }else {
                        new OkHttpRequest.Builder().url(LinkContext.MODIFY_PASSWORD)
                                .addParams("userName","userName")
                                .addParams("newPassword",editTxtNewPwd.getText().toString())
                                .post(new ResultCallback<String>() {
                                    @Override
                                    public void onError(Request request, Exception e) {
                                        showMsg("无法连接网络");
                                    }

                                    @Override
                                    public void onResponse(String response) {
                                        showMsg(response);
                                        finish();
                                    }
                                });
                    }
                }
            }
        });

    }
    private void showMsg(String str) {
        ToastView toastView = new ToastView(this, str);
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.show();
    }
}
