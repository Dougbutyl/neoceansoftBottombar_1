package com.neocean.baseactivitylibrary.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * User weixn
 * Date 2019/7/9
 */
public class StaggeredGridLayoutManager extends android.support.v7.widget.StaggeredGridLayoutManager {
    public StaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public StaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }
}
