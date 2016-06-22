package com.caidongdong.aestheticism.controller;

import android.content.Context;

import com.caidongdong.aestheticism.callback.home.IHomeView;
import com.caidongdong.aestheticism.entity.HeaderMenu;
import com.caidongdong.aestheticism.entity.HomeImg;

import java.util.List;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/9 10:34
 * 邮箱：mircaidong@163.com
 */
public class HomeController {
    private Context context;
    private IHomeView homeView;

    public HomeController(Context context, IHomeView homeView) {
        this.context = context;
        this.homeView = homeView;
    }

    public void getHomeImgList(List<HomeImg> list) {

        homeView.onGetHomeImgList(list);
    }

    public void getHomeMenuList(List<HeaderMenu> list) {

        homeView.onGetHomeMenuList(list);
    }
}
