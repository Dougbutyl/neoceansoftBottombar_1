package com.neocean.baseactivitylibrary.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * User weixn
 * Date 2019/7/9
 */
public class LinearLayoutManager extends android.support.v7.widget.LinearLayoutManager {
    public LinearLayoutManager(Context context) {
        super(context);
    }

    public LinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public LinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
