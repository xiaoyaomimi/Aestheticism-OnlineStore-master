package com.caidongdong.aestheticism.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.activity.OrderConfirmActivity;
import com.caidongdong.aestheticism.adapter.CartItemAdapter;
import com.caidongdong.aestheticism.adapter.CartListAdapter;
import com.caidongdong.aestheticism.callback.CartItemSelectCallBack;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.entity.CartItem;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.caidongdong.aestheticism.view.ToastView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/2 15:19
 * 邮箱：mircaidong@163.com
 */
public class CartFragment extends ContentFragment implements CartItemSelectCallBack,View.OnClickListener{
    private Context context;
    private XRecyclerView xRecyclerView;
    private List<CartItem> cartList;
    private CartItemAdapter cartAdapter;
    private CheckBox selectAll;
    private TextView itemsCount;
    private ImageView cartEmpty;
    private ImageView networkEnable;
    private LinearLayout gotoDetail;
    private int selectItemNumber = 0;
    private TextView cartTxtEmptyNote;
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        context = getActivity();
        initView(view);
        return view;
    }

    private void initView(View view) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemsCount = (TextView) view.findViewById(R.id.cart_items_count);
        cartEmpty = (ImageView) view.findViewById(R.id.imgv_cart_page_empty);
        networkEnable = (ImageView) view.findViewById(R.id.imgv_cart_page_internet_error);
        gotoDetail = (LinearLayout) view.findViewById(R.id.layout_cart_to_pay);
        selectAll = (CheckBox) view.findViewById(R.id.checkbox_select_all);
        cartTxtEmptyNote = (TextView) view.findViewById(R.id.cart_txt_empty_note);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.cart_item_xrecyclerlist);
        gotoDetail.setOnClickListener(this);
        itemsCount.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        xRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SquareSpin);

//        cartList = new ArrayList<CartItem>();
//        CartItem order = new CartItem("01","111","[天猫超市]蓝月亮洗衣液薰衣草香亮增白衣物护理","$22","$11","2",0);
//        CartItem order1 = new CartItem("02","111","[天猫超市]蓝月亮洗衣液薰衣草香亮增白衣物护理","$22","$11","2",0);
//        CartItem order2 = new CartItem("03","111","[天猫超市]蓝月亮洗衣液薰衣草香亮增白衣物护理","$22","$11","2",0);
//        cartList.add(order);
//        cartList.add(order1);
//        cartList.add(order2);
//        cartAdapter = new CartItemAdapter(cartList,context);


    }
    @Override
    public void initdata(Bundle savedInstanceState) {
        getCartItemList();
    }

    @Override
    public void cancelSelectAll() {
        selectAllOrCancel(false);
    }

    @Override
    public void selectAll() {
        selectAllOrCancel(true);
    }

    @Override
    public void selectItemsCount() {
        if (cartList != null &&  !cartList.isEmpty()) {
            int number = 0;
            for (int i = 0; i < cartList.size(); i++) {
                if (cartList.get(i).getStatus() == 1) {
                    number++;
                }
            }
            if (number == cartList.size()) {
                selectAllOrCancel(true);
            } else {
                selectAllOrCancel(false);
            }
            setItemsCount(number);
            selectItemNumber = number;
        }
    }

    @Override
    public void cartIsEmpty() {
//        cartEmpty.setVisibility(View.VISIBLE);
        cartTxtEmptyNote.setVisibility(View.VISIBLE);
    }

    private void selectAllOrCancel(boolean status) {
        if (cartList != null &&  !cartList.isEmpty()) {
            selectAll.setChecked(status);
            if (status) {
                setItemsCount(cartList.size());
                selectItemNumber = cartList.size();
            } else {
                setItemsCount(0);
                selectItemNumber = 0;
            }
        }
    }

    private void setItemsCount(int number) {
        itemsCount.setText("结算(" + number + ")");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.layout_cart_to_pay:
                //统计选中了多少商品
                if (selectItemNumber == 0) {
                    Toast.makeText(context,"还没有选择物品",Toast.LENGTH_SHORT).show();
                }else if (selectItemNumber > 0) {
                    //传递数据，跳转页面
                    intent = new Intent(getActivity(),OrderConfirmActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.checkbox_select_all:
                if (cartList != null && cartList.size() > 0) {
                    for (int i = 0; i < cartList.size(); i ++) {
                        if (selectAll.isChecked()) {
                            cartList.get(i).setStatus(1);
                            cartAdapter.setSelectAllStatus(true);
                        }else {
                            cartList.get(i).setStatus(0);
                        }
                    }
                    cartAdapter.notifyDataSetChanged();
                }
                if (cartList != null &&  !cartList.isEmpty()) {
                    if (selectAll.isChecked()) {
                        selectAllOrCancel(true);
                    } else {
                        selectAllOrCancel(false);
                    }
                }else {
                    selectAll.setChecked(false);
                    showMsg("购物车是空的噢");
                }
                break;
           default:
               break;
        }
    }
    private void getCartItemList() {
        new OkHttpRequest.Builder().url(LinkContext.GET_CART_LIST).addParams("userId","userId").post(new ResultCallback<List<CartItem>>() {
            @Override
            public void onError(Request request, Exception e) {
                showMsg("网络连接失败");
                networkEnable.setVisibility(View.VISIBLE);
            }

            @Override
            public void onResponse(List<CartItem> list) {
                if (list != null && !list.isEmpty()) {
                    cartList = list;
                    cartAdapter = new CartItemAdapter(cartList,context);
                    cartAdapter.setCartItemCallBack(CartFragment.this);
                    xRecyclerView.setAdapter(cartAdapter);
                    initListView();
                }else {
                    cartEmpty.setVisibility(View.VISIBLE);
                }

            }
        });
    }
    private void showMsg(String str) {
        ToastView toastView = new ToastView(getActivity().getApplication(), str);
        toastView.setGravity(Gravity.CENTER, 0, 0);
        toastView.show();
    }

    private void initListView() {
        xRecyclerView.setLoadingMoreEnabled(true);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                cartAdapter.notifyDataSetChanged();
                xRecyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {

            }
        });

        cartAdapter.setOnItemClickListener(new CartItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                Toast.makeText(context,"Hi there!"+position, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
