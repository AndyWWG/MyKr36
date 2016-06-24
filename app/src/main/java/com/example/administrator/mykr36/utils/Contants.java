package com.example.administrator.mykr36.utils;

import com.example.administrator.mykr36.utils.entity.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class Contants {

    public static final String URL = "http://36kr.com/columns/starding";

    public static List<CategoryBean> categoryBeanList() {
        List<CategoryBean> categoryBeanList = new ArrayList<CategoryBean>();
        categoryBeanList.add(new CategoryBean("全部", "http://www.36kr.com/columns/starding", "all"));
        categoryBeanList.add(new CategoryBean("早期项目", "http://www.36kr.com/columns/starding", "starding"));
        categoryBeanList.add(new CategoryBean("B轮后", "http://www.36kr.com/columns/bplus", "bplus"));
        categoryBeanList.add(new CategoryBean("大公司", "http://www.36kr.com/columns/company", "company"));
        categoryBeanList.add(new CategoryBean("资本", "http://www.36kr.com/columns/deep", "deep"));
        categoryBeanList.add(new CategoryBean("深度", "http://www.36kr.com/columns/deep", "deep"));
        categoryBeanList.add(new CategoryBean("研究", "http://www.36kr.com/columns/research", "research"));
        return categoryBeanList;
    }
}
