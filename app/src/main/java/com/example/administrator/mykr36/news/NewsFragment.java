package com.example.administrator.mykr36.news;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mykr36.R;
import com.example.administrator.mykr36.news.adapter.NewsRecyclerAdapter;
import com.example.administrator.mykr36.news.entity.ArticleBean;
import com.example.administrator.mykr36.utils.Contants;
import com.example.administrator.mykr36.utils.Diver;
import com.example.administrator.mykr36.utils.MessageEvent;
import com.example.administrator.mykr36.utils.OkHttpUtils;
import com.example.administrator.mykr36.utils.RecyclerViewDivider;
import com.example.administrator.mykr36.utils.entity.CategoryBean;
import com.example.administrator.mykr36.utils.ui.BaseFragment;
import com.example.administrator.mykr36.utils.ui.HomeActivity;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class NewsFragment extends BaseFragment {
    private ImageView mTopBar;
    private RecyclerView mNewsRecyclerView;
    private NewsRecyclerAdapter mNewsAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private TextView mTopBarTitle;
    private CategoryBean mCategoryBean = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ArticleBean> articleBeanList;
    private int lastItem;
    //解决上拉重复加载的bug
    private boolean isMore = true;

    @Override
    protected int getLayout() {
        return R.layout.fragment_news_layout;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注册EventBus
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁EventBus 使用EventBus必须要加上这句
        EventBus.getDefault().unregister(this);
    }


    /**
     * 接收eventbus传过来的值  并在textview中更新数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveMessage(MessageEvent event) {
        mCategoryBean = event.getCategoryBean();
        mTopBarTitle.setText(mCategoryBean.getmTitle());
        if (mCategoryBean.getmType().equals("all")) {
            //updateNewsData（）更新数据的方法  传URL过去重新对服务器发起请求
            updateNewsData(Contants.URL);

        } else {
            updateNewsData(mCategoryBean.getmHref());
        }
    }

    /**
     * 实例化组件
     */
    @Override
    protected void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.news_refresh);
        mTopBar = (ImageView) mView.findViewById(R.id.news_top_bar);
        mNewsRecyclerView = (RecyclerView) mView.findViewById(R.id.news_list_recycler);
        mTopBarTitle = (TextView) mView.findViewById(R.id.news_top_text);

    }

    /**
     * 获取颜色
     *
     * @param color
     * @return
     */
    private int getColor(int color) {
        return getResources().getColor(color);
    }


    @Override
    protected void initVarible() {
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.red));
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mNewsRecyclerView.setHasFixedSize(true);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        mNewsRecyclerView.setLayoutManager(mLinearLayoutManager);
        mNewsRecyclerView.addItemDecoration(new Diver(getActivity(), getResources().getColor(R.color.red), 3));
        mNewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mCategoryBean != null && !mCategoryBean.getmType().equals("all")) {
            mNewsAdapter = new NewsRecyclerAdapter(0, getActivity());
            updateNewsData(mCategoryBean.getmHref());
        } else {
            mNewsAdapter = new NewsRecyclerAdapter(0, getActivity());
            updateNewsData(Contants.URL);
        }


        //设置RecyclerView固定大小
        mNewsRecyclerView.setHasFixedSize(true);
        //设置布局管理器
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, true);
        mNewsRecyclerView.setLayoutManager(mLinearLayoutManager);

        //设置适配器
        mNewsAdapter = new NewsRecyclerAdapter(0, getActivity());
        mNewsAdapter.setmArticleBeanList(articleBeanList);

        //设置分割线
        mNewsRecyclerView.addItemDecoration(new RecyclerViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
        mNewsRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();


    }


    @Override
    protected void initListener() {
        //是news_top_bar 打开侧滑菜单的点击事件
        mTopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).openDrawerLayout();
            }
        });
        mNewsAdapter.setmOnRecyclerViewClickListener(new NewsRecyclerAdapter.OnRecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, ArticleBean articleBean, int position) {
                Toast.makeText(getActivity(), "position = " + position + "," + articleBean.getmTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        //刷新监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mCategoryBean == null) {
                    updateNewsData(Contants.URL);
                } else {
                    updateNewsData(mCategoryBean.getmHref());
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 5000);
            }
        });
        /**
         * 监听RecyclerView滑动事件,上拉加载
         */
        mNewsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == recyclerView.SCROLL_STATE_IDLE && lastItem + 1 == mLinearLayoutManager.getItemCount()) {
                    if (isMore) {
                        isMore = false;
                        String loadMoreURL = null;
                        if (mCategoryBean != null) {
                            loadMoreURL = mCategoryBean.getmHref() + "?b_url_code=" + articleBeanList.get(articleBeanList.size() - 1).getmId() + "&d=next";
                            Log.e("------", "---------" + loadMoreURL);
                        } else {
                            loadMoreURL = Contants.URL + "?b_url_code=" + articleBeanList.get(articleBeanList.size() - 1).getmId() + "?d=next";
                            Log.e("------", "---------" + loadMoreURL);
                        }
                        OkHttpUtils.getAsync(loadMoreURL, new OkHttpUtils.DataCallBack() {
                            @Override
                            public void requestFailure(Request request, IOException e) {
                                Log.e("zuliang", "新闻数据加载失败！！！");
                            }

                            @Override
                            public void requestSuccess(String result) {
                                Document document = Jsoup.parse(result, Contants.URL);
                                List<ArticleBean> temp = ArticleBean.getArticleBeans(document);
                                articleBeanList.addAll(temp);
                                mNewsAdapter.notifyDataSetChanged();
                                isMore = true;

                            }
                        });
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //找到最后一个选项的position
                lastItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
            }
        });
    }

    @Override
    protected void bindData() {

    }

    /**
     * 更新数据
     *
     * @param url
     */
    private void updateNewsData(String url) {
        OkHttpUtils.getAsync(url, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.e("zuliang", "新闻数据加载失败！！！");
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, Contants.URL);
                articleBeanList = ArticleBean.getArticleBeans(document);
                mNewsAdapter.setmArticleBeanList(articleBeanList);
                mNewsRecyclerView.setAdapter(mNewsAdapter);
                mNewsAdapter.notifyDataSetChanged();
            }
        });
    }

}
