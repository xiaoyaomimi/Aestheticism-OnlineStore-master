package com.caidongdong.aestheticism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.caidongdong.aestheticism.R;


/**
 * Aestheticism
 * 作者：caidongdong on 2016/1/15 09:57
 * 邮箱：mircaidong@163.com
 */
public class ItemIntroduceWebviewFragment extends Fragment {
    private String webAddress;
    private WebView webView;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_webview_item_introduce,null);
        initView();
        return view;
    }

    private void initView() {
        webView = (WebView) view.findViewById(R.id.item_detial_webview);
        WebSettings webSettings = webView.getSettings();
        //使用缓存
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //支持javascript
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://www.baidu.com");
        //.....
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
