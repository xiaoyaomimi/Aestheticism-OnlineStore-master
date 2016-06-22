package com.caidongdong.aestheticism.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.adapter.ItemIntroduceImgAdapter;
import com.caidongdong.pulltorefreshscrollview.library.PullToRefreshBase;
import com.caidongdong.pulltorefreshscrollview.library.PullToRefreshScrollView;
import com.caidongdong.tagview.Tag;
import com.caidongdong.tagview.TagListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/1/18 09:49
 * 邮箱：mircaidong@163.com
 */
public class ItemIntroduceFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private ViewPager imgItemViewpager;
    private LinearLayout pointViewGroup;
    private TagListView tagListView;
    private PullToRefreshScrollView pullToRefreshScrollView;
    private ImageView[] tips;
    private ImageView[] imageViews;
    private int[] imageArray;
    private final List<Tag> mTags = new ArrayList<Tag>();
    private final String[] titles = {"安全必备", "音乐", "父母学", "上班族必备",
            "手机卫", "QQ", "输入法", "微信", "最美应用", "AndevUI", "蘑菇街"};
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_introduce,null);
        ButterKnife.bind(view);
        imgItemViewpager = (ViewPager) view.findViewById(R.id.img_item_viewpager);
        pointViewGroup = (LinearLayout) view.findViewById(R.id.point_view_group);
        tagListView = (TagListView) view.findViewById(R.id.tag_list_view);
        pullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);
        pullToRefreshScrollView.setScrollingWhileRefreshingEnabled(true);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                pullToRefreshScrollView.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_layout_content,new ItemIntroduceWebviewFragment()).commit();
                pullToRefreshScrollView.onRefreshComplete();
            }
        });
        initData();
        setUpData();
        tagListView.setTags(mTags);
        return view;
    }

    /**
     * 初始化tag
     */
    private void setUpData() {
        for (int i = 0; i < 10; i++) {
            Tag tag = new Tag();
            tag.setId(i);
            tag.setChecked(true);
            tag.setTitle(titles[i]);
            mTags.add(tag);
        }
    }

    private void initData() {
        imageArray = new int[]{R.mipmap.img01, R.mipmap.img02, R.mipmap.img03, R.mipmap.img04};
        tips = new ImageView[imageArray.length];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.dot_focus);
            } else {
                tips[i].setBackgroundResource(R.mipmap.dot_blur);
            }

//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((ViewGroup.MarginLayoutParams) (new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT)));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            pointViewGroup.addView(imageView, layoutParams);
        }
        //将图片装载到数组中
        imageViews = new ImageView[imageArray.length];
        for (int i = 0; i < imageViews.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageViews[i] = imageView;
//            imageView.setBackgroundResource(imageArray[i]);
            Picasso.with(getActivity()).load(imageArray[i]).resize(983, 600).centerCrop().into(imageViews[i]);
        }
        imgItemViewpager.setAdapter(new ItemIntroduceImgAdapter(imageViews));
        imgItemViewpager.setOnPageChangeListener(this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        imgItemViewpager.setCurrentItem((imageViews.length) * 100);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position % imageViews.length);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.dot_focus);
            } else {
                tips[i].setBackgroundResource(R.mipmap.dot_blur);
            }
        }
    }
}
