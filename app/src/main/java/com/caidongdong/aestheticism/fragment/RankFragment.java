package com.caidongdong.aestheticism.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.entity.Info;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.squareup.okhttp.Request;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by caidongdong on 2015/11/19.
 */
public class RankFragment extends ContentFragment {
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView2)
    TextView textView2;

    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_rank, null);
        return view;
    }

    @Override
    public void initdata(Bundle savedInstanceState) {
//        String url = "http://images.csdn.net/20150817/1.jpg";
//        new OkHttpRequest.Builder().url(url).imageView(imageView).displayImage(null);
//        String url1 = "https://raw.githubusercontent.com/hongyangAndroid/okhttp-utils/master/users.gson";
        String url = "http://172.16.193.74:8080/Aestheticism_Server/LoginService";
        new OkHttpRequest.Builder().url(url).addParams("username","cdd").addParams("password","123").post(new MyResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("TAG", "onError , e = " + e.getMessage());
            }

            @Override
            public void onResponse(String response) {
                textView2.setText(response);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public abstract class MyResultCallback<T> extends ResultCallback<T> {

        @Override
        public void onBefore(Request request) {
            super.onBefore(request);
//            setTitle("loading...");
        }

        @Override
        public void onAfter() {
            super.onAfter();
//            setTitle("Sample-okHttp");
        }
    }
}
