package com.caidongdong.aestheticism.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.config.LinkContext;
import com.caidongdong.aestheticism.entity.CartItem;
import com.caidongdong.aestheticism.net.okhttp.callback.ResultCallback;
import com.caidongdong.aestheticism.net.okhttp.request.OkHttpRequest;
import com.caidongdong.aestheticism.view.MyListView;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OrderConfirmActivity extends Activity implements OnClickListener {
    @Bind(R.id.radio_pay_weixin)
    RadioButton radioPayWeixin;
    @Bind(R.id.type_to_pay_weixin)
    RelativeLayout typeToPayWeixin;
    @Bind(R.id.radio_pay_zhifubao)
    RadioButton radioPayZhifubao;
    @Bind(R.id.type_to_pay_zhifubao)
    RelativeLayout typeToPayZhifubao;
    @Bind(R.id.text_total_price)
    TextView textTotalPrice;
    @Bind(R.id.text_total_num)
    TextView textTotalNum;
    private RelativeLayout changeReceiveAddress;
    private TextView orderConfirm;
    private ImageButton imgbBack;
    private MyListView listView;
    private ArrayList<HashMap<String, Object>> listItems;
    private List<CartItem> dataList;
    private double allTotalPrice;
    private int allTotalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_confirm);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        changeReceiveAddress = (RelativeLayout) findViewById(R.id.order_detial_confirm_address);
        orderConfirm = (TextView) findViewById(R.id.order_detial_confirm);
        imgbBack = (ImageButton) findViewById(R.id.order_detial_confirm_back);
        listView = (MyListView) findViewById(R.id.cart_order_confirm_listview);
    }

    private void initData() {
        CartItem cm1 = new CartItem("01", "111", "[天猫超市]蓝月亮洗衣液薰衣草香亮增白衣物护理", "22", "11", "2", 0);
        CartItem cm2 = new CartItem("01", "111", "[天猫超市]蓝月亮洗衣液薰衣草香亮增白衣物护理", "22", "11", "2", 0);
        dataList = new ArrayList<CartItem>();
        dataList.add(cm1);
        dataList.add(cm2);
//        dataList.add(cm2);
//        dataList.add(cm2);
//        dataList.add(cm2);
//        dataList.add(cm2);
        allTotalPrice = 0;
        allTotalNum = 0;
        listItems = new ArrayList<HashMap<String, Object>>();
        listView.setAdapter(new SimpleAdapter(this, listItems, R.layout.confirm_order_listview_item,
                new String[]{"goods_name", "goods_number", "goods_price"},
                new int[]{R.id.order_confirm_goods_name, R.id.order_confirm_goods_number, R.id.order_confirm_goods_price}));
        for (int i = 0; i < dataList.size(); i++) {
            CartItem cartItem = dataList.get(i);
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("goods_name", cartItem.getItemName());
            item.put("goods_number", cartItem.getNum() + "份  x");
            item.put("goods_price", "￥" + cartItem.getSellPrice());
            double totalPrice = Double.parseDouble(cartItem.getNum()) * Double.parseDouble(cartItem.getSellPrice());
            allTotalPrice += totalPrice;
            allTotalNum += Integer.parseInt(cartItem.getNum());
            listItems.add(item);
        }
        textTotalPrice.setText("￥" + allTotalPrice + "");
        textTotalNum.setText("共" + allTotalNum + "份");
    }

    private void initListener() {
        changeReceiveAddress.setOnClickListener(this);
        orderConfirm.setOnClickListener(this);
        imgbBack.setOnClickListener(this);
        typeToPayWeixin.setOnClickListener(this);
        typeToPayZhifubao.setOnClickListener(this);
        radioPayZhifubao.setOnClickListener(this);
        radioPayWeixin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_detial_confirm_address:
                Intent address = new Intent(this, MyAddressActivity.class);
                startActivity(address);
                break;
            case R.id.order_detial_confirm:
                payForOrder(this);
                Intent status = new Intent(this, OrderSuccessActivity.class);
                startActivity(status);
                finish();
                break;
            case R.id.order_detial_confirm_back:
                finish();
                break;
            case R.id.type_to_pay_weixin:
                if (radioPayZhifubao.isChecked()) {
                    radioPayZhifubao.setChecked(false);
                }
                radioPayWeixin.setChecked(true);
                break;
            case R.id.type_to_pay_zhifubao:
                if (radioPayWeixin.isChecked()) {
                    radioPayWeixin.setChecked(false);
                }
                radioPayZhifubao.setChecked(true);
                break;
            case R.id.radio_pay_zhifubao:
                radioPayWeixin.setChecked(false);
                break;
            case R.id.radio_pay_weixin:
                radioPayZhifubao.setChecked(false);
                break;
        }
    }
    public void payForOrder(final Context context) {
        new OkHttpRequest.Builder().url(LinkContext.PAY_FOR_THE_BILL).addParams("userName","userName").post(new ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
