package com.caidongdong.aestheticism.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.entity.CartItem;

import java.util.List;

/**
 * Aestheticism
 * Created by caidong on 16-2-29.
 * email:mircaidong@163.com
 */
public class CartListAdapter extends BaseAdapter {

    private List<CartItem> orderList;
    private Context context;
    private LinearLayout zeroLayout;

    public CartListAdapter(List<CartItem> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, null);
            zeroLayout = (LinearLayout) convertView.findViewById(R.id.favorite_zero_layout);
            if (position == 0 ) {
                zeroLayout.setVisibility(View.VISIBLE);
            }else {
                zeroLayout.setVisibility(View.GONE);
            }
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.radiobtn_order_item);
            holder.itemImg = (ImageView) convertView.findViewById(R.id.img_oder_item);
            holder.itemName = (TextView) convertView.findViewById(R.id.tv_oder_item_describe);
            holder.oldPrice = (TextView) convertView.findViewById(R.id.order_item_oldPrice);
            holder.sellPrice = (TextView) convertView.findViewById(R.id.order_item_sellPrice);
            holder.num = (TextView) convertView.findViewById(R.id.order_item_num);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        CartItem  order = new CartItem();
        order = orderList.get(position);
        if (order != null) {
            holder.itemName.setText(order.getItemName());
//            holder.itemImg.setImageResource();
            holder.oldPrice.setText(order.getOldPrice());
            holder.oldPrice.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.sellPrice.setText(order.getSellPrice());
            holder.num.setText(order.getNum());
        }
        return convertView;
    }

    class ViewHolder{
        CheckBox checkBox;
        ImageView itemImg;
        TextView itemName;
        TextView oldPrice;
        TextView sellPrice;
        TextView num;
    }
}
