package com.example.administrator.mykr36.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Auther Created by xzl on 2016/6/17 16:23.
 * E-mail zuliang_xie@sina.com
 *
 * 分割线
 */
public class Diver extends RecyclerView.ItemDecoration{

    private int mDividerHeight = 1;
    private static final int[] ATTRsS = new int[]{android.R.attr.listDivider};
    private Drawable mDiveder;
    private Paint mPaint;

    /**
     * 系统默认的分割线
     * @param context
     */
    public Diver(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRsS);
        mDiveder = a.getDrawable(0);
        a.recycle();
    }

    /**
     * 自定义分割线
     * @param context
     * @param color   风格线的颜色
     * @param height 分割线高度
     */
    public Diver(Context context, int color, int height){
        this(context);
        mDividerHeight = height;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 绘制分隔线
     * left right top bottom
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int left = parent.getPaddingLeft();
        int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        int childSize = parent.getChildCount();
        for (int i=0;i<childSize;i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDividerHeight;
            if (mDiveder != null){
                mDiveder.setBounds(left,top,right,bottom);
                mDiveder.draw(c);
            }
            if (mPaint != null){
                c.drawRect(left,top,right,bottom,mPaint);
            }
        }
    }

    /**
     * 获取分割线的尺寸
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,mDividerHeight);
    }
}
