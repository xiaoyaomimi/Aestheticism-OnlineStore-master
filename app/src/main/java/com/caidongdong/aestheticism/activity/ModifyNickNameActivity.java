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

public class ModifyNickNameActivity extends Activity {

    @Bind(R.id.edit_text_nickname)
    EditText editTextNickname;
    @Bind(R.id.personinfo_btn_nick_name_confirm)
    Button personinfoBtnNickNameConfirm;
    @Bind(R.id.imbtn_nickname_back)
    ImageButton imbtnNicknameBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_modify_nick_name);
        ButterKnife.bind(this);
        editTextNickname.setHint("夏晓培");
        imbtnNicknameBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        personinfoBtnNickNameConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextNickname.getText().toString();
                if (name != null && !name.isEmpty()) {
                    new OkHttpRequest.Builder().url(LinkContext.MODIFY_NICK_NAME).addParams("new_nick_name",name).post(new ResultCallback<String>() {
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
                    showMsg("请输入新的昵称");
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
