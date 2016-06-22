package com.caidongdong.aestheticism.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.activity.ItemIntroduceActivity;
import com.caidongdong.aestheticism.adapter.GridViewAdapter;
import com.caidongdong.aestheticism.adapter.RecyleViewAdapter;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.entity.GoodsItem;
import com.caidongdong.aestheticism.entity.HeaderMenu;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.caidongdong.aestheticism.view.SlideShowView;
import com.caidongdong.aestheticism.view.ToastView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by caidongdong on 2015/11/19.
 */
public class IndexFragment extends ContentFragment{



    private Context context;
    private List<GoodsItem> mDatas;
    private List<HeaderMenu> headerMenuList;
    private XRecyclerView xrecyclerview;
    private RecyleViewAdapter recyleViewAdapter;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;
    private SlideShowView slideShowView;
    private int MORE_MENU_GOODS_ITEMS = 1;
    private int MENU_GOODS_ITEMS = 0;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_index, null);
        context = getActivity();
        headerMenuList = new ArrayList<HeaderMenu>();
        HeaderMenu headerMenu = new HeaderMenu();
        headerMenu.setDescribe("娱乐");
        headerMenu.setImgUrl(R.mipmap.headermenu+"");
        HeaderMenu headerMenu1 = new HeaderMenu();
        headerMenu1.setDescribe("KTV");
        headerMenu1.setImgUrl(R.mipmap.ktv+"");
        HeaderMenu headerMenu2 = new HeaderMenu();
        headerMenu2.setDescribe("餐饮");
        headerMenu2.setImgUrl(R.mipmap.food + "");
        HeaderMenu headerMenu3 = new HeaderMenu();
        headerMenu3.setDescribe("电影");
        headerMenu3.setImgUrl(R.mipmap.movie + "");
        headerMenuList.add(headerMenu);
        headerMenuList.add(headerMenu1);
        headerMenuList.add(headerMenu2);
        headerMenuList.add(headerMenu3);

//        recyleViewAdapter = new RecyleViewAdapter(context,mDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xrecyclerview = (XRecyclerView)view.findViewById(R.id.xrecyclerview);
        xrecyclerview.setLayoutManager(layoutManager);
        xrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xrecyclerview.setLaodingMoreProgressStyle(ProgressStyle.SquareSpin);
//        xrecyclerview.setArrowImageView(R.drawable.iconfont_downgrey);
        View headercontent = inflater.inflate(R.layout.recyclerview_header,null);
        View header =  inflater.inflate(R.layout.recyclerview_header, (ViewGroup)(headercontent.findViewById(android.R.id.content)),false);
        gridView = (GridView)header.findViewById(R.id.gridview);
        slideShowView = (SlideShowView) header.findViewById(R.id.slideview);
        gridView.setNumColumns(4);
        gridViewAdapter = new GridViewAdapter(getActivity(), headerMenuList);
        xrecyclerview.addHeaderView(header);
        xrecyclerview.setAdapter(new RecyleViewAdapter(context,null));
        initWidgetEvent();
        return view;
    }

    private void initWidgetEvent() {
//        xrecyclerview.setAdapter(recyleViewAdapter);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "你好" + position, Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public View getView() {
        return super.getView();
    }

    @Override
    public void initdata(Bundle savedInstanceState) {
        mDatas = new ArrayList<GoodsItem>();
        getGoodsItemsList(MENU_GOODS_ITEMS);
//        downLoadApp();
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
        slideShowView.stopPlay();
        slideShowView.destoryBitmaps();
    }

    @Override
    public void onPause() {
        super.onPause();
        slideShowView.stopPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        slideShowView.startPlay();
    }

     private void getGoodsItemsList(final int type) {
         String address;
         if (type == 0) {
             address = LinkContext.HOME_GOODS_LIST;
         }else{
             address = LinkContext.MORE_HOME_GOODS_LIST;
         }

         new OkHttpRequest.Builder().url(address).addParams("goods_items","goods").post(new ResultCallback<List<GoodsItem>>() {
             @Override
             public void onError(Request request, Exception e) {
                 ToastView toastView = new ToastView(getActivity().getApplication(), "无法连接网络");
                 toastView.setGravity(Gravity.CENTER, 0, 0);
                 toastView.show();
                 if (type == 0) {
                     xrecyclerview.refreshComplete();
                 } else {
                     xrecyclerview.loadMoreComplete();
                 }

             }

             @Override
             public void onResponse(List<GoodsItem> list) {
                 if (list != null && !list.isEmpty()) {
//                    mDatas = list;
//                     ToastView toastView = new ToastView(getActivity(), list.get(0).getName());
//                     toastView.setGravity(Gravity.CENTER, 0, 0);
//                     toastView.show();
                     if (type == 0) {
                         mDatas = list;
                         recyleViewAdapter = new RecyleViewAdapter(context, mDatas);
                         xrecyclerview.setAdapter(recyleViewAdapter);
                         xrecyclerview.refreshComplete();
                     } else {
                         for (int i = 0; i < list.size(); i++) {
                             mDatas.add(list.get(i));
                         }
//                         Toast.makeText(context,"list大小为：" +mDatas.size(),Toast.LENGTH_SHORT).show();
                         recyleViewAdapter.notifyDataSetChanged();
                         xrecyclerview.loadMoreComplete();
                     }
                     initListener();
                 }
             }
         });
     }

    private void initListener() {
        recyleViewAdapter.setOnItemClickListener(new RecyleViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(context,"Hi there!"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(getActivity(), ItemIntroduceActivity.class);
                startActivity(intent);
            }
        });
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mDatas.clear();
                getGoodsItemsList(MENU_GOODS_ITEMS);
            }

            @Override
            public void onLoadMore() {
                getGoodsItemsList(MORE_MENU_GOODS_ITEMS);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getActivity(),"lllladfadf",Toast.LENGTH_SHORT).show();
    }

    public void downLoadApp() {
        final String fileDir =  Environment.getExternalStorageDirectory() + "/";
        File file = new File(fileDir);
//        if (!file.exists()) {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        new OkHttpRequest.Builder()
                .url("http://172.16.193.74:8080/Aestheticism_Server/test.apk")
                .addParams("download","file")
                .destFileDir(fileDir)
                .destFileName("testApkll.apk")
                .download(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(context,"下载成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
