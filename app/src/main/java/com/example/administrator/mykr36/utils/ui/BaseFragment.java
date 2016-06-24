package com.example.administrator.mykr36.utils.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public abstract class BaseFragment extends Fragment {
    protected View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {
            mView = inflater.inflate(getLayout(), container, false);
        }
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initVarible();
        initListener();
    }

    /**
     * 获取子类布局文件
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化控件
     */
    protected abstract void initView();

    /**
     * 初始化变量
     */
    protected abstract void initVarible();

    /**
     * 初始化事件处理
     */
    protected abstract void initListener();

    /**
     * 绑定数据
     */
    protected abstract void bindData();
}
