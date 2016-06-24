package com.example.administrator.mykr36.news.ui;

import android.os.Bundle;

import com.example.administrator.mykr36.R;
import com.example.administrator.mykr36.utils.entity.CategoryBean;
import com.example.administrator.mykr36.utils.ui.BaseFragment;

/**
 * Created by Administrator on 2016/6/15 0015.
 */
public class PagerFragment extends BaseFragment {
    private static final String KEY = "EXTRA";
    private CategoryBean mCategoryBean;


    @Override
    protected int getLayout() {
        return R.layout.fragment_news_pager_layout;
    }

    @Override
    protected void initView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCategoryBean = (CategoryBean) bundle.getSerializable(KEY);
        }

    }

    @Override
    protected void initVarible() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void bindData() {

    }


    /**
     * 用于新建CategoryBean对应的Fragment实例，实现Fragment的复用
     *
     * @return
     */
    public static final PagerFragment newInstance(CategoryBean categoryBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, categoryBean);
        PagerFragment fragment = new PagerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}
