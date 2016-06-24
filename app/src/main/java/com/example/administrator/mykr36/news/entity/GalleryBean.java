package com.example.administrator.mykr36.news.entity;


import com.example.administrator.mykr36.utils.ImageUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Auther Created by xzl on 2016/6/17 13:05.
 * E-mail zuliang_xie@sina.com
 *
 * 新闻首页头部广告轮滑图片实体
 */
public class GalleryBean {
    private String mTitle;//标题
    private String mImageUrl;//图片路径
    private String mHref;//文章详情地址
    private String mType;//文章类型

    public GalleryBean(String mTitle, String mImageUrl, String mHref, String mType) {
        super();
        this.mTitle = mTitle;
        this.mImageUrl = mImageUrl;
        this.mHref = mHref;
        this.mType = mType;
    }

    /**
     * 获取首页广告轮播的图片数据
     * @param document
     * @return
     */
    public static List<GalleryBean> getGalleryBeans(Document document){
        List<GalleryBean> galleryBeanList = new ArrayList<GalleryBean>();
        Elements elements = document.select("div.head-images");
        Elements links = elements.first().select("a[data-lazyload]");  //带有data-lazyload属性的a元素
        for (Element element :
                links) {
            String imgurl = ImageUtils.getCutImageURL(element.attr("data-lazyload"));
            String href = element.attr("abs:href");
            String mask = element.select("span").first().text();
            String title = element.text();
            galleryBeanList.add(new GalleryBean(title,imgurl,href,mask));
        }
        return galleryBeanList;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmHref() {
        return mHref;
    }

    public void setmHref(String mHref) {
        this.mHref = mHref;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    @Override
    public String toString() {
        return "GalleryBean{" +
                "mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mHref='" + mHref + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}
