package com.caidongdong.aestheticism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caidongdong.aestheticism.R;

import java.util.List;

/**
 * Aestheticism
 * 作者：caidongdong on 2015/12/25 14:42
 * 邮箱：mircaidong@163.com
 */
public class RecyclerViewOrderAdapter extends RecyclerView.Adapter<RecyclerViewOrderAdapter.MyViewHolder> implements View.OnClickListener {
    private List<String> stringList;
    private Context context;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public RecyclerViewOrderAdapter(Context context,List<String> stringList) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_list_item_new, parent, false);
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter_seller_item, parent,false));
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        if (position == 0) {
            holder.zeroLayout.setVisibility(View.VISIBLE);
        }else {
            holder.zeroLayout.setVisibility(View.INVISIBLE);
        }
//        holder.tv.setText(stringList.get(position));

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount()
    {
        return stringList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        LinearLayout zeroLayout;
        public MyViewHolder(View view) {
            super(view);
//            tv = (TextView) view.findViewById(R.id.store_name);
            zeroLayout = (LinearLayout) view.findViewById(R.id.favorite_zero_layout);
        }

    }
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , int position);
    }
}
