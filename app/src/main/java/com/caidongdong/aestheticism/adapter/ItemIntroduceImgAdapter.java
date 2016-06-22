package com.caidongdong.aestheticism.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/1/8 14:15
 * 邮箱：mircaidong@163.com
 */
public class ItemIntroduceImgAdapter extends PagerAdapter {
    private ImageView[] imageViews;

    public ItemIntroduceImgAdapter(ImageView[] imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView(imageViews[position % imageViews.length]);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager)container).addView(imageViews[position % imageViews.length], 0);
        return imageViews[position % imageViews.length];
    }
}
