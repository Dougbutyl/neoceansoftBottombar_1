package com.neocean.app.myapplication

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ImmersionBar
import com.neocean.baseactivitylibrary.swipac.SwipeBackActivity


/**
 * User weixn
 * Date 2019/6/20
 */
abstract class BaseActivity : SwipeBackActivity() {


    lateinit var immersionBar: ImmersionBar
    override final fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        immersionBar = ImmersionBar.with(this)
        setContentView(layoutId())
        if (isImmersion()) {
            //沉浸式
            immersionBar.init()
        } else if (fullScreen()) {
            //全屏
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                //19以上
                immersionBar.hideBar(BarHide.FLAG_HIDE_BAR).fullScreen(true).init()
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        } else if (statusBarColor() != 0) {
            immersionBar.statusBarColor(statusBarColor()).fitsSystemWindows(true).init()
        }
        setSwipeBackEnable(slidingBack())
        main(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    abstract fun main(savedInstanceState: Bundle?)
    abstract fun layoutId(): Int
    /**
     * 设置沉浸式状态栏
     */
    open fun isImmersion(): Boolean {
        return false
    }

    /**
     * 全屏设置
     */
    open fun fullScreen(): Boolean {
        return false
    }

    /**
     * 状态栏颜色
     */
    open fun statusBarColor(): Int {
        return 0
    }

    /**
     * 默认不启动滑动返回
     * 启用需要设置style    <item name="android:windowIsTranslucent">true</item>
     */
    open fun slidingBack():Boolean{
        return false
    }


}