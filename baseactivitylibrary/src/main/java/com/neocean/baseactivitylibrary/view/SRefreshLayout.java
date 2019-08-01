package com.neocean.baseactivitylibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;

/**
 * User weixn
 * Date 2019/7/30
 */
public class SRefreshLayout extends LinearLayout {
    private TextView mHeaderView;
    private boolean isRefreashing;
    private float mLastY = -1;//按下的起始高度
    private int mHeaderViewHeight;//headerView内容高度
    private int mHeight;//布局高度
    private float mStartY;

    interface OnRefreshListener {
        void onRefresh();
    }

    public OnRefreshListener mOnRefreshListener;

    public SRefreshLayout(Context context) {
        super(context);
        initView(context);
    }

    public SRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mHeaderView = new TextView(context);
        mHeaderView.setText("刷新布局文件");

        setOrientation(VERTICAL);
        addView(mHeaderView, 0);
        mHeaderView.getLayoutParams().height = 0;
        getHeaderViewHeight();
        getViewHeight();
    }

    /**
     * 获取headView高度
     */
    private void getHeaderViewHeight() {
        ViewTreeObserver vto2 = mHeaderView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeaderViewHeight = mHeaderView.getHeight();
                mHeaderView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    /**
     * 获取SRefreshLayout当前实例的高度
     */
    private void getViewHeight() {
        ViewTreeObserver thisView = getViewTreeObserver();
        thisView.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                SRefreshLayout.this.mHeight = SRefreshLayout.this.getHeight();
                SRefreshLayout.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        AbsListView absListView = null;
        for (int n = 0; n < getChildCount(); n++) {
            if (getChildAt(n) instanceof AbsListView) {
                absListView = (ListView) getChildAt(n);

            }
        }
        if (absListView == null)
            return super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float space = ev.getRawY() - mStartY;
                if (space > 0 && !absListView.canScrollVertically(-1) && absListView.getFirstVisiblePosition() == 0) {
                    return true;
                } else {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1)
            mLastY = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录起始高度
                mLastY = ev.getRawY();//记录按下时的Y坐标
                break;
            //手指离开屏幕时
            case MotionEvent.ACTION_UP:
                //松开时
                //避免点击事件触发
                if (!isRefreashing)
                    break;
                //如果headView状态处于READY状态 则说明松开时应该进入REFRESHING状态
//                if (mHeaderView.getStatus() == SRefreshHeader.STATE_READY) {
//                    mHeaderView.setState(SRefreshHeader.STATE_REFRESHING);
//                    if (mOnRefreshListener != null)
//                        mOnRefreshListener.onRefresh();
//                }
//                //根据状态重置SrefreshLayout当前实例和headView高度
//                resetHeadView(mHeaderView.getStatus());
//                reset(mHeaderView.getStatus());
                mLastY = -1;//重置坐标
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isRefreashing)
                    isRefreashing = true;
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                //TODO
                break;
        }
        return super.onTouchEvent(ev);
    }


    private void reset(int status) {
        ViewGroup.LayoutParams lp = getLayoutParams();
//        switch (status) {
//            case SRefreshHeader.STATE_REFRESHING:
//                lp.height = mHeight + mHeaderViewHeight;
//                break;
//            case SRefreshHeader.STATE_NORMAL:
//                lp.height = mHeight;
//                break;
//        }
        setLayoutParams(lp);
    }


    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }
}