package com.caidongdong.aestheticism.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.entity.HomeImg;
import com.caidongdong.aestheticism.manager.ImageLoaderManager;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.okhttp.Request;

import org.xmlpull.v1.XmlPullParser;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by caidong on 15-11-22.
 */

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果；
 * 既支持自动轮播页面也支持手势滑动切换页面
 *
 *
 */
public class SlideShowView extends FrameLayout{
    // 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
    private ImageLoader imageLoader = ImageLoader.getInstance();

    //轮播图图片数量
//    private final static int IMAGE_COUNT = 4;
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 5;
    //自动轮播启用开关
    private final static boolean isAutoPlay = true;

    //自定义轮播图的资源
//    private String[] imageUrls;
    private List<HomeImg> homeImgList;
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    //放圆点的View的list
    private List<View> dotViewsList;

    private ViewPager viewPager;
    //当前轮播页
    private int currentItem  = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;

    private Context context;
    private MyPagerAdapter pagerAdapter;

    //Handler
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }

    };

    public SlideShowView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        if (!imageLoader.isInited()) {
            ImageLoaderManager.initImageLoader(context);
        }
        initData();
        if(isAutoPlay){
            startPlay();
        }

    }
    /**
     * 开始轮播图切换
     */
    public void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
        imageLoader.resume();
    }
    /**
     * 停止轮播图切换
     */
    public void stopPlay(){
        scheduledExecutorService.shutdownNow();
        imageLoader.pause();

    }
    /**
     * 初始化相关Data
     */
    public void initData(){
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();
        HomeImg homeImg1 = new HomeImg("file:///storage/emulated/0/PTT/notice/noticeimage/JPG_1457514405087.jpg","s01",11);
        HomeImg homeImg2 = new HomeImg("file:///storage/emulated/0/PTT/notice/noticeimage/JPG_1457514424209.jpg","s01",11);
        HomeImg homeImg3 = new HomeImg("file:///storage/emulated/0/PTT/notice/noticeimage/JPG_1457575183138.jpg","s01",11);
        homeImgList = new ArrayList<HomeImg>();
        homeImgList.add(homeImg1);
        homeImgList.add(homeImg2);
        homeImgList.add(homeImg3);
        initUI(context);
        // 一步任务获取图片

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getHomeImgList("s01");
//            }
//        }).start();
//        new GetListTask().execute("");
    }
    /**
     * 初始化Views等UI
     */
    private void initUI(Context context){
        if(homeImgList == null || homeImgList.isEmpty())
            return;

        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);

        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();

        // 热点个数与图片特殊相等
        for (int i = 0; i < homeImgList.size(); i++) {
            ImageView view =  new ImageView(context);
            view.setTag(homeImgList.get(i).getUrl());
            if(i==0)//给一个默认图
                view.setBackgroundResource(R.mipmap.appmain_subject_1);
            view.setScaleType(ScaleType.FIT_XY);
            imageViewsList.add(view);

            ImageView dotView =  new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            params.leftMargin = 4;
            params.rightMargin = 4;
            dotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);
        pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
        getHomeImgList(homeImgList.get(0).getVcode());
    }

    /**
     * 填充ViewPager的页面适配器
     *
     */
    private class MyPagerAdapter  extends PagerAdapter {

        @Override
        public void destroyItem(View container, int position, Object object) {
            // TODO Auto-generated method stub
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, final int position) {
            ImageView imageView = imageViewsList.get(position);
            LabelView labelView = new LabelView(context);
            labelView.setText("Hot");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2,-2);
            labelView.setLayoutParams(layoutParams);
            labelView.setPadding(0,0,0,0);
            labelView.setTextSize(12);
            //TODO
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"你好"+position,Toast.LENGTH_SHORT).show();
                }
            });

            imageLoader.displayImage(imageView.getTag() + "", imageView);

            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

    }
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     */
    private class MyPageChangeListener implements OnPageChangeListener {

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
                case 1:// 手势滑动，空闲中
                    isAutoPlay = false;
                    break;
                case 2:// 界面切换中
                    isAutoPlay = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        viewPager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub

            currentItem = pos;
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.mipmap.dot_focus);
                }else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.mipmap.dot_blur);
                }
            }
        }

    }

    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    /**
     * 销毁ImageView资源，回收内存
     *
     */
    public void destoryBitmaps() {

        if (homeImgList != null && !homeImgList.isEmpty()) {
            for (int i = 0; i < homeImgList.size(); i++) {
                ImageView imageView = imageViewsList.get(i);
                Drawable drawable = imageView.getDrawable();
                if (drawable != null) {
                    //解除drawable对view的引用
                    drawable.setCallback(null);
                }
            }
        }
        imageLoader.stop();
        imageLoader.clearMemoryCache();
        imageLoader.clearDiskCache();
    }


    /**
     * 异步任务,获取数据
     *
     */
//    class GetListTask extends AsyncTask<String, Integer, Boolean> {
//
//        @Override
//        protected Boolean doInBackground(String... params) {
////            try {
////                    // 这里一般调用服务端接口获取一组轮播图片，下面是从百度找的几个图片
////
////                    imageUrls = new String[]{
////                            "file:///storage/emulated/0/PTT/notice/image/IMG_1450515405559.jpg",
////                            "file:///storage/emulated/0/PTT/notice/image/IMG_1450515405561.jpg",
////                            "file:///storage/emulated/0/PTT/notice/image/IMG_1450662200151.jpg",
////                            "file:///storage/emulated/0/PTT/notice/image/IMG_1450690057805.jpg"
////                    };
////                return true;
////            } catch (Exception e) {
////                e.printStackTrace();
//                return false;
////            }
//
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            super.onPostExecute(result);
//            if (result) {
//                initUI(context);
//            }
//        }
//    }

    private void getHomeImgList(String vcode) {

        new OkHttpRequest.Builder().url(LinkContext.HOME_IMG_ADDRESS).addParams("VCODE",vcode).post(new ResultCallback<List<HomeImg>>() {
            @Override
            public void onError(Request request, Exception e) {
//                ToastView toastView = new ToastView(context, "无法连接网络");
//                toastView.setGravity(Gravity.CENTER,0,0);
//                toastView.show();
            }

            @Override
            public void onResponse(List<HomeImg> list) {
                if (list != null && !list.isEmpty()) {
                    //把得到的数据存入数据库，并且刷新当前的list
                    //更新数据库

                    //刷新播放列表
                    homeImgList = list;
                    pagerAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
    }


}



