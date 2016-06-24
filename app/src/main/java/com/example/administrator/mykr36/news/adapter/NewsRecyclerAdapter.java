package com.example.administrator.mykr36.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.mykr36.R;
import com.example.administrator.mykr36.news.entity.ArticleBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/6/16 0016.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final int NORMAL = 0;//新闻列表视图
    private static final int OTHER = 1;
    private static final int FOOTER = 2;//上拉加载更多布局
    private int mType = 0;              //默认是新闻列表视图
    private LayoutInflater mInflater;
    private Context mContext;
    private OnRecyclerViewClickListener mOnRecyclerViewClickListener;
    private List<ArticleBean> mArticleBeanList;

    /**
     * 获取数据
     */
    public void setmArticleBeanList(List<ArticleBean> mArticleBeanList) {
        this.mArticleBeanList = mArticleBeanList;
    }

    public NewsRecyclerAdapter(int mType, Context mContext) {
        this.mType = mType;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 是根据viewType去加载不同的ViewHolder
     * 创建ItemView,并返回ItemView对象
     *
     * @param parent   父容器，RecyclerView
     * @param viewType 视图加载类型
     * @return ItemView实例，对象 ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        //新建View,用于加载我的ItemView
        if (viewType == NORMAL) {
            //新闻视图
            if (itemView == null) {
                itemView = mInflater.inflate(R.layout.news_fragment_items, parent, false);
                return new NewsNormalViewHoler(itemView);
            } else if (viewType == FOOTER) {
                //其他视图    上拉加载更多布局
                View footItemView = mInflater.inflate(R.layout.recycler_load_more_layout, parent, false);
                FooterItemViewHolder footerItemViewHolder = new FooterItemViewHolder(footItemView);
                return footerItemViewHolder;
            }
            return null;
        }
        //实例化ViewHolder，并把ItemView传给它

        //最后返回ViewHoler
        return null;
    }

    /**
     * 数据绑定
     *
     * @param holder   ItemView
     * @param position ItemView  id
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //判断holer是不是NewsViewHolder
        //instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
        if (holder instanceof NewsNormalViewHoler) {
            //设置数据到我的ItemView
            final ArticleBean articleBean = mArticleBeanList.get(position);
            holder.itemView.setTag(articleBean);
            ((NewsNormalViewHoler) holder).mNewsTitle.setText(articleBean.getmTitle());
            ((NewsNormalViewHoler) holder).mNewsType.setText(articleBean.getmTitle());
            ((NewsNormalViewHoler) holder).mNewsAuther.setText(articleBean.getmTitle());
            ((NewsNormalViewHoler) holder).mTime.setText(articleBean.getmTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewClickListener != null) {
                        mOnRecyclerViewClickListener.onItemClick(v, (ArticleBean) v.getTag(), position);//???
                    }
                }
            });
            Picasso.with(mContext).load(articleBean.getmImgUrl()).into(((NewsNormalViewHoler) holder).mNewLogo);
            ((NewsNormalViewHoler) holder).mNewsTitle.setText(articleBean.getmTitle());
            ((NewsNormalViewHoler) holder).mNewsType.setText(articleBean.getmMask());
            ((NewsNormalViewHoler) holder).mNewsAuther.setText(articleBean.getmAuthor().getmName());
            ((NewsNormalViewHoler) holder).mTime.setText(articleBean.getmDateText());
        } else if (holder instanceof FooterItemViewHolder) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnRecyclerViewClickListener != null) {
                        mOnRecyclerViewClickListener.onItemClick(v, (ArticleBean) v.getTag(), position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mArticleBeanList.size();
    }

    /**
     * 是判断RecyclerView所要加载的是哪个视图
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOTER;
        } else {
            return NORMAL;
        }
    }


    /**
     * 新闻列表的视图
     * 继承 RecyclerView.ViewHolder
     */
    class NewsNormalViewHoler extends RecyclerView.ViewHolder {
        private TextView mNewsTitle, mNewsType, mNewsAuther, mTime;
        private ImageView mNewLogo;

        public NewsNormalViewHoler(View itemView) {
            super(itemView);
            mNewsTitle = (TextView) itemView.findViewById(R.id.news_items_title);
            mNewsType = (TextView) itemView.findViewById(R.id.news_items_type);
            mNewsAuther = (TextView) itemView.findViewById(R.id.news_items_auther);
            mTime = (TextView) itemView.findViewById(R.id.news_items_time);
            mNewLogo = (ImageView) itemView.findViewById(R.id.news_items_logo);
        }
    }


    /**
     * 上拉刷新加载更多布局
     */
    private class FooterItemViewHolder extends RecyclerView.ViewHolder {

        public FooterItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 提供给外部调用者的一个监听接口
     */
    public interface OnRecyclerViewClickListener {
        void onItemClick(View view, ArticleBean articleBean, int position);
    }

    /**
     * 将这个监听接口暴露给外部调用者
     *
     * @param mOnRecyclerViewClickListener
     */
    public void setmOnRecyclerViewClickListener(OnRecyclerViewClickListener mOnRecyclerViewClickListener) {
        this.mOnRecyclerViewClickListener = mOnRecyclerViewClickListener;
    }
}
