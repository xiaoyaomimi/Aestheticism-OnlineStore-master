package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.caidongdong.aestheticism.view.RoundImageView;
import com.caidongdong.aestheticism.view.ToastView;
import com.squareup.okhttp.Request;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends Activity {
    private static final int REQUEST_IMAGE = 2;
    @Bind(R.id.modify_user_nick_name)
    RelativeLayout modifyUserNickName;
    @Bind(R.id.modify_user_user_phone_num)
    RelativeLayout modifyUserUserPhoneNum;
    @Bind(R.id.modify_user_common_address)
    RelativeLayout modifyUserCommonAddress;
    @Bind(R.id.radio_sex_man)
    RadioButton radioSexMan;
    @Bind(R.id.radio_sex_woman)
    RadioButton radioSexWoman;
    @Bind(R.id.headerview)
    RoundImageView headerview;
    @Bind(R.id.user_nick_name)
    TextView userNickName;
    @Bind(R.id.user_phone_num)
    TextView userPhoneNum;
    @Bind(R.id.user_select_address)
    TextView userSelectAddress;
    private ArrayList<String> mSelectPath;
    @Bind(R.id.image_head)
    RelativeLayout imageHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initView();
        initListenner();
    }

    /**
     * 初始化页面
     */
    private void initView() {
        //头像
        Bitmap bitmap = BitmapFactory.decodeFile("");
        if (bitmap != null) {
            headerview.setImageBitmap(bitmap);
        } else {
            headerview.setImageResource(R.mipmap.beauty);
        }
        //昵称
        String nickName = "";
        if (nickName != null) {
            userNickName.setText(nickName);
        }
        //电话
        String phoneNum = "";
        if (phoneNum != null) {
            userPhoneNum.setText(phoneNum);
        }
        //地址
        String address = "";
        if (address != null) {
            userSelectAddress.setText(address);
        }
        radioSexWoman.setChecked(true);
        radioSexWoman.setEnabled(false);
        radioSexMan.setEnabled(false);
    }

    /**
     * 绑定监听事件
     */
    private void initListenner() {
        imageHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MultiImageSelectorActivity.class);
                // whether show camera
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
                // select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_SINGLE);
                // max select image amount
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
        modifyUserNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, ModifyNickNameActivity.class);
                startActivity(intent);
            }
        });
        modifyUserUserPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, ModifyPhoneNumActivity.class);
                startActivity(intent);
            }
        });
        modifyUserCommonAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(SettingActivity.this, MyAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                StringBuilder sb = new StringBuilder();
                for (String p : mSelectPath) {
                    sb.append(p);
                }
                final String filePath = sb.toString();
                File file = new File(sb.toString());
                boolean exists = file.exists();
                if (!exists) {
                    showMsg("图片不存在");
                }
                new OkHttpRequest.Builder().url(LinkContext.UPLOAD_HEAD_IMG).addHeader("Content-Disposition", "form-data").files(new Pair<String, File>("headImg", file)).upload(new ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                        showMsg("网络连接失败");
                    }

                    @Override
                    public void onResponse(String response) {
                        showMsg(response);
                        //TODO 将头像路径写入数据库
                        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                        if (bitmap != null) {
                            headerview.setImageBitmap(bitmap);
                        }
                    }
                });
            }
        }
    }

    private void showMsg(String str) {
        ToastView toastView = new ToastView(this, str);
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.show();
    }


}
