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
import com.squareup.okhttp.Request;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ModifyPhoneNumActivity extends Activity {

    @Bind(R.id.imgbtn_phone_back)
    ImageButton imgbtnPhoneBack;
    @Bind(R.id.edit_text_phone)
    EditText editTextPhone;
    @Bind(R.id.personinfo_btn_phone_confirm)
    Button personinfoBtnPhoneConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_modify_phone_num);
        ButterKnife.bind(this);
        editTextPhone.setHint("11111111111");
        imgbtnPhoneBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        personinfoBtnPhoneConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = editTextPhone.getText().toString();
                if (phone != null && !phone.isEmpty()) {
                    new OkHttpRequest.Builder().url(LinkContext.MODIFY_PHONE_NUMBER).addParams("new_phone_num",phone).post(new ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            showMsg("网络连接失败，请检查网络");
                        }

                        @Override
                        public void onResponse(String response) {
                            showMsg(response);
                            finish();
                        }
                    });
                }else {
                    showMsg("请输入新的电话");
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
