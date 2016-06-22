package com.caidongdong.aestheticism.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.callback.CartItemSelectCallBack;
import com.caidongdong.aestheticism.entity.CartItem;

import java.util.List;

/**
 * Aestheticism
 * 作者：caidongdong on 2016/3/2 15:57
 * 邮箱：mircaidong@163.com
 */
public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.MyViewHolder> implements OnClickListener  {
    private List<CartItem> orderList;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private boolean selectAll = false;
    private CartItemSelectCallBack cartCB;
    private LinearLayout zeroLayout;
    public CartItemAdapter(List<CartItem> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        if (position == 0 ) {
            zeroLayout.setVisibility(View.VISIBLE);
        }else {
            zeroLayout.setVisibility(View.GONE);
        }
        CartItem cartItem = orderList.get(position);
        holder.itemName.setText(cartItem.getItemName());
        holder.oldPrice.setText("￥" + cartItem.getOldPrice());
        if (cartItem.getSellPrice() != null) {
            holder.oldPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            holder.oldPrice.setTextColor(Color.GRAY);
            holder.sellPrice.setText("￥" + cartItem.getSellPrice());
        }else {
            holder.sellPrice.setVisibility(View.GONE);
        }

        holder.num.setText(cartItem.getNum());
        if (cartItem.getStatus() == 0) {
            holder.checkBox.setChecked(false);
        }else if (cartItem.getStatus() == 1){
            holder.checkBox.setChecked(true);
        }
        holder.itemView.setTag(position);
        holder.delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                orderList.remove(position);
                notifyDataSetChanged();
                if (getItemCount() == 0) {
                    cartCB.cartIsEmpty();
                }
            }
        });
        holder.checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectAll){
                    cancelSelectAll();
                }
                if (orderList.get(position).getStatus() == 1) {
                    orderList.get(position).setStatus(0);
                    if (selectAll) {
                        cancelSelectAll();
                        selectAll = false;
                    }
                    reCountelectItems();
                }else {
                    orderList.get(position).setStatus(1);
                    reCountelectItems();
                }
            }
        });
        holder.reduce.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem item = orderList.get(position);
                int num = Integer.parseInt(item.getNum());
                if (num > 1) {
                    item.setNum((num - 1) + "");
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context,"不能再减了噢",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem item = orderList.get(position);
                int num = Integer.parseInt(item.getNum());
                if (num < 99) {
                    item.setNum((num + 1) + "");
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context,"目前一个商品只能订购99个",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setSelectAllStatus(boolean status) {
        selectAll = status;
    }
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public CartItem getItem(int postion) {
        return orderList.get(postion);
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        ImageView itemImg;
        TextView itemName;
        TextView oldPrice;
        TextView sellPrice;
        TextView num;
        ImageButton delete;
        ImageButton reduce;
        ImageButton add;

        public MyViewHolder(View view) {
            super(view);
            checkBox = (CheckBox) view.findViewById(R.id.radiobtn_order_item);
            itemImg = (ImageView) view.findViewById(R.id.img_oder_item);
            itemName = (TextView) view.findViewById(R.id.tv_oder_item_describe);
            oldPrice = (TextView) view.findViewById(R.id.order_item_oldPrice);
            sellPrice = (TextView) view.findViewById(R.id.order_item_sellPrice);
            num = (TextView) view.findViewById(R.id.order_item_num);
            delete = (ImageButton) view.findViewById(R.id.cart_btn_delete_item);
            reduce = (ImageButton) view.findViewById(R.id.cart_btn_img_reduce);
            add = (ImageButton) view.findViewById(R.id.cart_btn_img_add);
            zeroLayout = (LinearLayout) view.findViewById(R.id.favorite_zero_layout);
        }

    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }

    public void setCartItemCallBack(CartItemSelectCallBack callBack) {
        this.cartCB = callBack;
    }

    public void cancelSelectAll() {
        this.cartCB.cancelSelectAll();
    }

    public void selectAllItem() {
        this.cartCB.selectAll();
    }

    public void reCountelectItems() {
        this.cartCB.selectItemsCount();
    }
}
