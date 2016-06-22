package com.caidongdong.aestheticism.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.caidongdong.aestheticism.R;
import com.caidongdong.aestheticism.entity.GoodsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * AestheticismApplication
 * 作者：caidongdong on 2015/12/8 11:13
 * 邮箱：mircaidong@163.com
 */
public class RecyleViewAdapter extends RecyclerView.Adapter<RecyleViewAdapter.MyViewHolder> implements View.OnClickListener{

    private List<GoodsItem> mDatas;
    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RecyleViewAdapter(Context context,List<GoodsItem> mDatas) {
        this.mDatas = mDatas;
        this.context = context;
    }

    @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_adapter_seller_item, parent, false);
//            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_adapter_seller_item, parent,false));
            MyViewHolder holder = new MyViewHolder(view);
            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            GoodsItem goodsItem = mDatas.get(position);
            Picasso.with(context).load(goodsItem.getImgUrl()).into(holder.imgUrl);
            holder.name.setText(goodsItem.getName());
            holder.monthSales.setText("月銷量" + goodsItem.getMonthSales()+"");
            holder.describe.setText(goodsItem.getDescribe());
            holder.averageTime.setText(goodsItem.getAverageTime()+"分钟");
            if (goodsItem.getLevel() == 0) {
                holder.level.setImageResource(R.mipmap.star05);
            }
            if (goodsItem.getDeliveryType() == 0) {
                holder.deliveryType.setText("第三方配送");
            }else {
                holder.deliveryType.setText("店铺配送");
            }
//            holder.name.setText(mDatas.get(position));
            holder.itemView.setTag(position);
        }

        @Override
        public int getItemCount()
        {
            if (mDatas != null) {
                return mDatas.size();
            }else {
                return 0;
            }
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

            ImageView imgUrl;
            TextView name;
            ImageView level;
            TextView monthSales;
            TextView describe;
            TextView deliveryType;
            TextView averageTime;
            public MyViewHolder(View view) {
                super(view);
                imgUrl = (ImageView) view.findViewById(R.id.goods_item_image);
                name = (TextView) view.findViewById(R.id.goods_item_name);
                level = (ImageView) view.findViewById(R.id.goods_item_level);
                monthSales = (TextView) view.findViewById(R.id.goods_item_monthsales);
                describe = (TextView) view.findViewById(R.id.goods_item_describe);
                deliveryType = (TextView) view.findViewById(R.id.goods_item_delivery);
                averageTime = (TextView) view.findViewById(R.id.goods_item_average_time);
            }

        }
        public static interface OnRecyclerViewItemClickListener {
            void onItemClick(View view , int position);
        }
    }

