package com.caidongdong.aestheticism.fragment;


import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by caidongdong on 2015/11/19.
 */
public class FragmentFactory {
    public static final int TAB_INDEX = 0;
    public static final int TAB_BUY_WHAT = 1;
    public static final int TAB_ORDER = 2;
    public static final int TAB_ME = 3;

    //缓存所有的Fragment对象
    public static HashMap<Integer,ContentFragment> fragmentHashMap = new HashMap<Integer, ContentFragment>();

    public static Fragment createFragment(int position) {
        ContentFragment mFragment = fragmentHashMap.get(position);
        if (mFragment == null) {
            switch (position){
                case TAB_INDEX:
                    mFragment = new IndexFragment();
                    break;
                case TAB_BUY_WHAT:
                    mFragment = new CartFragment();
                    break;
                case TAB_ORDER:
                    mFragment = new OrderFragment();
                    break;
                case TAB_ME:
                    mFragment = new MeFragment();
                    break;
            }
            fragmentHashMap.put(position,mFragment);
        }
        return mFragment;
    }
    public static Fragment getFragment(int postion) {
        if (fragmentHashMap.get(postion) == null) {
           return createFragment(postion);
        }else {
            return fragmentHashMap.get(postion);
        }
    }
}
