package com.example.administrator.mykr36.utils.ui;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.RadioGroup;

import com.example.administrator.mykr36.R;
import com.example.administrator.mykr36.equity.EquityFragment;
import com.example.administrator.mykr36.find.FindFragment;
import com.example.administrator.mykr36.me.MeFragment;
import com.example.administrator.mykr36.news.NewsFragment;
import com.example.administrator.mykr36.utils.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private RadioGroup radioGroup;
    private ViewPager mViewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<Fragment> mFragmentList;
    private DrawerLayout mDrawerLayout;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //实例化组件
        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_container);
        radioGroup = (RadioGroup) findViewById(R.id.home_bottom_radiogroup);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //设置ViewPager的缓存个数
        mViewPager.setOffscreenPageLimit(3);

        //获取适配器对象
        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        //获取Fragment集合对象
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new NewsFragment());
        mFragmentList.add(new EquityFragment());
        mFragmentList.add(new FindFragment());
        mFragmentList.add(new MeFragment());
        //将Fragment集合传入给Adapter

        myViewPagerAdapter.setmFragmentList(mFragmentList);
        //设置适配器
        mViewPager.setAdapter(myViewPagerAdapter);
    }

    @Override
    protected void initVarible() {
        radioGroup.check(R.id.home_tab_news);
    }

    @Override
    protected void initListener() {
        //注册点击事件
        radioGroup.setOnCheckedChangeListener(this);
        mViewPager.setOnPageChangeListener(this);

    }

    /**
     * 关闭侧滑菜单
     */
    public void closeDrawerLayout() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }

    /**
     * 打开侧滑菜单
     */
    public void openDrawerLayout() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void bindData() {

    }


    /**
     * viewpager切换兼点击事件
     * 也就是底部四个Fragment绑定的viewpager的点击事件
     *
     * @param group
     * @param checkedId
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home_tab_news:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.home_tab_equity:
                mViewPager.setCurrentItem(1, true);
                break;
            case R.id.home_tab_find:
                mViewPager.setCurrentItem(2, true);
                break;
            case R.id.home_tab_me:
                mViewPager.setCurrentItem(3, true);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * 底部四个Fragment绑定的viewpager的滑动事件
     * (Position是下标)
     *
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.home_tab_news);
                break;
            case 1:
                radioGroup.check(R.id.home_tab_equity);
                break;
            case 2:
                radioGroup.check(R.id.home_tab_find);
                break;
            case 3:
                radioGroup.check(R.id.home_tab_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
