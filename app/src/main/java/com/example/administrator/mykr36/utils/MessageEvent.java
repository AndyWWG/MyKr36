package com.example.administrator.mykr36.utils;


import com.example.administrator.mykr36.utils.entity.CategoryBean;

/**
 * Auther Created by xzl on 2016/6/16 17:08.
 * E-mail zuliang_xie@sina.com
 * <p/>
 * 事件类
 */
public class MessageEvent {


    private CategoryBean categoryBean;

    public MessageEvent(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

    public CategoryBean getCategoryBean() {
        return categoryBean;
    }

    public void setCategoryBean(CategoryBean categoryBean) {
        this.categoryBean = categoryBean;
    }

}
