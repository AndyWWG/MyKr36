package com.example.administrator.mykr36.utils.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.mykr36.R;
import com.example.administrator.mykr36.utils.entity.CategoryBean;

import java.util.List;


/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class FixedPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int CATEGORY = 0;
    private LayoutInflater mInflater;
    private List<CategoryBean> CategoryList;
    private int mType;
    private onRecyclerViewClickListener monRecyclerViewClickListener;


    public void setCategoryList(List<CategoryBean> categoryList) {

        CategoryList = categoryList;
    }

    public FixedPagerAdapter(Context context, int type) {
        this.mType = type;
        mInflater = LayoutInflater.from(context);
    }


    /**
     * 创建viewholder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == CATEGORY) {
            itemView = mInflater.inflate(R.layout.category_items, parent, false);
//            itemView.setOnClickListener(this);
            ItemViewHolder itemViewHolder = new ItemViewHolder(itemView);

            return itemViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return mType;
    }


    /**
     * 绑定viewhodlew布局
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            CategoryBean categoryBean = CategoryList.get(position);
            holder.itemView.setTag(categoryBean);
            ((ItemViewHolder) holder).mItemType.setBackgroundColor(Color.RED);
            ((ItemViewHolder) holder).mItemName.setText(categoryBean.getmTitle());
            if (monRecyclerViewClickListener != null)
            {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        monRecyclerViewClickListener.onItemClick(v,(CategoryBean) v.getTag());
                    }
                });
            }
        }
    }

    /**
     * 返回一个CategoryList长度
     *
     * @return
     */
    @Override
    public int getItemCount() {

        return CategoryList.size();
    }


    /**
     * 复用一个item的控件
     */
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private View mItemType;
        private TextView mItemName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mItemName = (TextView) itemView.findViewById(R.id.category_item_name);
            mItemType = itemView.findViewById(R.id.category_item_type);
        }
    }

    /**
     * 定义一个提供外部的点击事件接口
     */
    public static interface onRecyclerViewClickListener {
        void onItemClick(View view, CategoryBean categoryBean);
    }


    /**
     * 绑定onItemClick方法
     * v.getTag()注意onBindViewHolder设置数据标识
     * @param v
     */
//    @Override
//    public void onClick(View v) {
//        if (monRecyclerViewClickListener != null) {
//            monRecyclerViewClickListener.onItemClick(v, (CategoryBean) v.getTag());
//        }
//
//    }

    /**
     * 将这个点击事件暴露给外部调用者
     * @param monRecyclerViewClickListener
     */
    public void setMonRecyclerViewClickListener(onRecyclerViewClickListener monRecyclerViewClickListener) {
        this.monRecyclerViewClickListener = monRecyclerViewClickListener;
    }
}
