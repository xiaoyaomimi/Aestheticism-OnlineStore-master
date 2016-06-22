package com.caidongdong.aestheticism.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.activity.AboutActivity;
import com.caidongdong.aestheticism.activity.ModifyPasswordActivity;
import com.caidongdong.aestheticism.activity.MyAddressActivity;
import com.caidongdong.aestheticism.activity.SettingActivity;
import com.caidongdong.aestheticism.helper.AppUpdateHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caidongdong on 2015/11/19.
 */
public class MeFragment extends ContentFragment {

    private RelativeLayout userSetting;
    private Button loginLogout;
    private RelativeLayout modifyPassword;
    private RelativeLayout aboutus;
    private RelativeLayout updateApp;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_me, null);
        loginLogout = (Button) view.findViewById(R.id.login_loginout);
        userSetting = (RelativeLayout) view.findViewById(R.id.user_setting);
        modifyPassword = (RelativeLayout) view.findViewById(R.id.user_setting_modify_pwd);
        aboutus = (RelativeLayout) view.findViewById(R.id.user_setting_info);
        updateApp = (RelativeLayout) view.findViewById(R.id.user_setting_update_apk);
        loginLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "dianji", Toast.LENGTH_SHORT).show();
            }
        });
        userSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ModifyPasswordActivity.class);
                startActivity(intent);
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
        updateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), MyAddressActivity.class);
//                startActivity(intent);
                AppUpdateHelper appUpdateHelper = new AppUpdateHelper(getActivity());
                appUpdateHelper.checkAppUpdate();
                Toast.makeText(getActivity(), "dianji", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void initdata(Bundle savedInstanceState) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
