package com.example.administrator.mykr36.utils.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.mykr36.R;
import com.example.administrator.mykr36.utils.Contants;
import com.example.administrator.mykr36.utils.Diver;
import com.example.administrator.mykr36.utils.MessageEvent;
import com.example.administrator.mykr36.utils.OkHttpUtils;
import com.example.administrator.mykr36.utils.adapter.FixedPagerAdapter;
import com.example.administrator.mykr36.utils.entity.CategoryBean;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/6/14 0014.
 */
public class CategoryFragment extends BaseFragment {
    private static final String TAG = "CategoryFragment";
    private ImageView mBack;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FixedPagerAdapter mAdapter;
    private List<CategoryBean> mCategoryList;

    /**
     * 获取子类布局文件
     *
     * @return
     */
    @Override
    protected int getLayout() {
        return R.layout.fragment_category_tab;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        mBack = (ImageView) mView.findViewById(R.id.category_back);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recyclerView);
    }


    /**
     * 初始化变量
     */
    @Override
    protected void initVarible() {
        //设置布局管理器
        mLayoutManager = new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置RecyclerView的固定大小
        mRecyclerView.setHasFixedSize(true);
        //设置适配器
        mAdapter = new FixedPagerAdapter(getActivity(), 0);
        mRecyclerView.addItemDecoration(new Diver(getActivity()));

        OkHttpUtils.getAsync(Contants.URL, new OkHttpUtils.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.e("TAG", "网络请求失败！！！");
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, Contants.URL);
                mCategoryList = CategoryBean.getCategoryBeanData(document);
                mAdapter.setCategoryList(mCategoryList);
                mRecyclerView.setAdapter(mAdapter);
                //刷新适配器
                mAdapter.notifyDataSetChanged();
            }
        });

    }


    /**
     * 初始化事件处理
     */
    @Override
    protected void initListener() {
        //实现RecyclerView的ItemView的点击事件  也就是侧滑里的RecyclerView点击事件
        mAdapter.setMonRecyclerViewClickListener(new FixedPagerAdapter.onRecyclerViewClickListener() {
            @Override
            public void onItemClick(View view, CategoryBean categoryBean) {
//                Toast.makeText(getActivity(), "11111",Toast.LENGTH_SHORT).show();
                //发送给新闻界面
                EventBus.getDefault().post(new MessageEvent(categoryBean));

                ((HomeActivity) getActivity()).closeDrawerLayout();
            }
        });
        //返回图标（箭头）的点击事件
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).closeDrawerLayout();
            }
        });
    }


    /**
     * 绑定数据
     */
    @Override
    protected void bindData() {

    }
}
