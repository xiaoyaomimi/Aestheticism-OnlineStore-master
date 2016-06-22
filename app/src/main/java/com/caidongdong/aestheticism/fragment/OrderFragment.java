package com.caidongdong.aestheticism.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.adapter.RecyclerViewOrderAdapter;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by caidongdong on 2015/11/19.
 */
public class OrderFragment extends ContentFragment implements View.OnClickListener{
    private XRecyclerView xRecyclerViewOrder;
    private RecyclerViewOrderAdapter recyleViewAdapterOrder;
    private List<String> stringList;
    private TextView undone_order;
    private TextView done_order;
    private int orderStatus = 0;
    private FrameLayout layoutOrderEmpty;
    @Override
    public View initView(LayoutInflater inflater) {
        view = inflater.inflate(R.layout.fragment_order, null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerViewOrder = (XRecyclerView) view.findViewById(R.id.xrecyclerview_order);
        undone_order = (TextView) view.findViewById(R.id.undone_order);
        done_order = (TextView) view.findViewById(R.id.done_order);
        layoutOrderEmpty = (FrameLayout) view.findViewById(R.id.layout_order_isempty);
        initTitleMenu();
        xRecyclerViewOrder.setLayoutManager(layoutManager);
        xRecyclerViewOrder.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerViewOrder.setLaodingMoreProgressStyle(ProgressStyle.SquareSpin);
        stringList = new ArrayList<String>();
        for (int i = 'A'; i < 'D'; i++)
        {
            stringList.add("hhhhhh");
        }
        recyleViewAdapterOrder = new RecyclerViewOrderAdapter(getContext(),stringList);
        xRecyclerViewOrder.setAdapter(recyleViewAdapterOrder);
        return view;
    }

    @Override
    public void initdata(Bundle savedInstanceState) {
        initListenner();
    }

    private void initListenner() {
        undone_order.setOnClickListener(this);
        done_order.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.undone_order:
                if (orderStatus != 0) {
                    undone_order.setBackgroundResource(R.mipmap.e0_nav_left_selected);
                    undone_order.setTextColor(getActivity().getResources().getColor(R.color.theam_tittle_color));
                    done_order.setBackgroundResource(R.mipmap.e0_nav_right_normal);
                    done_order.setTextColor(getActivity().getResources().getColor(R.color.white));
                    orderStatus = 0;
                    if (xRecyclerViewOrder.getVisibility() != View.VISIBLE) {
                        xRecyclerViewOrder.setVisibility(View.VISIBLE);
                        layoutOrderEmpty.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case R.id.done_order:
                if (orderStatus != 1) {
                    undone_order.setBackgroundResource(R.mipmap.e0_nav_left_normal);
                    undone_order.setTextColor(getActivity().getResources().getColor(R.color.white));
                    done_order.setBackgroundResource(R.mipmap.e0_nav_right_selected);
                    done_order.setTextColor(getActivity().getResources().getColor(R.color.theam_tittle_color));
                    orderStatus = 1;
                    xRecyclerViewOrder.setVisibility(View.GONE);
                    layoutOrderEmpty.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private void initTitleMenu() {
        if (orderStatus == 0) {
            undone_order.setBackgroundResource(R.mipmap.e0_nav_left_selected);
            undone_order.setTextColor(getActivity().getResources().getColor(R.color.theam_tittle_color));
            undone_order.setText(R.string.unfinished_order);
            done_order.setBackgroundResource(R.mipmap.e0_nav_right_normal);
            done_order.setTextColor(getActivity().getResources().getColor(R.color.white));
            done_order.setText(R.string.finished_order);
            orderStatus = 0;
        }else {
            undone_order.setBackgroundResource(R.mipmap.e0_nav_left_normal);
            undone_order.setTextColor(getActivity().getResources().getColor(R.color.white));
            undone_order.setText(R.string.unfinished_order);
            done_order.setBackgroundResource(R.mipmap.e0_nav_right_selected);
            done_order.setTextColor(getActivity().getResources().getColor(R.color.theam_tittle_color));
            done_order.setText(R.string.finished_order);
            orderStatus = 1;
        }
    }
}
