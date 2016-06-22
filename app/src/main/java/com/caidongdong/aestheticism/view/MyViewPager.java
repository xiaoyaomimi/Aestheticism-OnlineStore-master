package com.caidongdong.aestheticism.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by caidongdong on 2015/11/19.
 */
public class MyViewPager extends ViewPager{
    public MyViewPager(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    public MyViewPager(Context context) {
        super(context);
    }
    //自定义的viewpager不要去拦截相应的事件，传递给内部控件去消费
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}
