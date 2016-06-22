package com.caidongdong.aestheticism.callback.home;

import com.caidongdong.aestheticism.entity.HeaderMenu;
import com.caidongdong.aestheticism.entity.HomeImg;

import java.util.List;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/9 10:14
 * 邮箱：mircaidong@163.com
 */
public interface IHomeView {
    public void onGetHomeImgList(List<HomeImg> list);
    public void onGetHomeMenuList(List<HeaderMenu> list);
    public void toast(String str);
}
