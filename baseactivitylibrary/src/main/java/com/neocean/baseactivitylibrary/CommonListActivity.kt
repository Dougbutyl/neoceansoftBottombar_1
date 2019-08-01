package com.neocean.app.myapplication

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout

import com.neocean.app.myapplication.adapter.CommonAdapter

import com.neocean.app.refreshrecyclerview.RefreshRecyclerView
import com.neocean.app.refreshrecyclerview.tools.DensityUtil
import com.neocean.baseactivitylibrary.R
import com.neocean.baseactivitylibrary.widget.LayoutManager
import kotlinx.android.synthetic.main.activity_commonlist.*

/**
 * User weixn
 * Date 2019/6/28
 */
abstract class CommonListActivity : BaseActivity() {


    var adapter: CommonAdapter? = null
    var layoutId = 0
    abstract fun listMain()
    abstract fun setTVTitle(): Any
    abstract fun setAdapterLayoutId(): Int
    abstract fun setAdapterDataList(): Any
    abstract fun bindAdapterData(holder: CommonAdapter.CommonViewHolder, position: Int)
    final override fun main(savedInstanceState: Bundle?) {
        initOtherView()
        initList()
        listMain()
    }

    final override fun layoutId(): Int {
        return R.layout.activity_commonlist
    }


    open fun setRefreshCompleteTextColor(color: Int) {
        rc.setRefreshTextColor(color)
    }

    open fun setTitleRlBg() = android.R.color.white


    fun setReturnButtonImageResouce(id: Int) {
        iv_back.setImageResource(id)
    }

    /**
     * 返回按钮点击事件
     */
    open fun returnButtonClick() {
        finish()

    }

    /**
     * 标题点击事件
     */
    open fun titleCilick() {

    }

    /**
     * 返回按钮
     */
    fun getReturnBtn() = iv_back

    /**
     * 获取标题
     */
    fun getTvTitle() = tv_title


    /**
     * 获取列表
     */
    fun getNeoceanRefreshRecyclerView() = rc

    open fun pullRefreshEnable() = false

    open fun loadMoreEnable() = false
    open fun layoutManager(): LayoutManager? {
        return null
    }

    fun initList() {
        if (layoutManager() == null)
            rc.layoutManager = LinearLayoutManager(this)
        else
            rc.layoutManager = layoutManager()
        setRefreshCompleteTextColor(R.color.gray_font)
        rc.setPullRefreshEnabled(pullRefreshEnable())
        rc.setLoadingMoreEnabled(loadMoreEnable())
        rc.setLoadingListener(loadingListener)
        layoutId = setAdapterLayoutId()
        adapter = ListAdapter(setAdapterDataList())
        rc.adapter = adapter

    }

    fun initOtherView() {
        var titleAny = setTVTitle()
        when (titleAny) {
            is String -> {
                if (titleAny == null)
                    throw Exception("名称不能为NULL")
                else
                    tv_title.text = titleAny
            }
            is Int -> {
                var titleInt = resources.getText(titleAny)
                if (titleInt == null)
                    throw Exception("不合法ID")
                else
                    tv_title.text = titleInt
            }
        }
        iv_back.setOnClickListener { returnButtonClick() }
        tv_title.setOnClickListener { titleCilick() }
        rl_title.background = resources.getDrawable(setTitleRlBg())


    }

    fun addRightView(view: View) {
        var param: RelativeLayout.LayoutParams? = null
        when (view) {
            is ImageView -> {
                param = RelativeLayout.LayoutParams(
                    DensityUtil.dip2px(baseContext, 25f),
                    DensityUtil.dip2px(baseContext, 25f)
                )
            }
            else -> param = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                DensityUtil.dip2px(baseContext, 25f)
            )
        }
        param?.addRule(RelativeLayout.CENTER_VERTICAL)
        param?.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        param?.rightMargin = DensityUtil.dip2px(baseContext, 20f)
        view.layoutParams = param
        rl_title.addView(view)
        view.setOnClickListener { rightViewClick(view) }
    }


    open fun rightViewClick(view: View) {}


    private val loadingListener = object : RefreshRecyclerView.LoadingListener {
        override fun onLoadMore() {
            loadMore()
        }

        override fun onRefresh() {
            refresh()
        }

    }

    fun loadMoreComplete() {
        rc.loadMoreComplete()
    }

    fun refreshComplete(string: String) {
        rc.refreshComplete(string)
    }

    fun refreshComplete() {
        rc.refreshComplete()
    }

    fun setNomore() {
        rc.setNoMore(true)
    }

    fun setNomore(string: String) {
        if (TextUtils.isEmpty(string))
            rc.setNoMore(true, "到底了")
        else
            rc.setNoMore(true, string)
    }

    open fun loadMore() {

    }

    open fun refresh() {

    }

    open fun itemClick(view: View?, positon: Int) {}
    open fun itemLongClick(view: View?, positon: Int) {}

    fun View.Toast(str: String) {
        android.widget.Toast.makeText(baseContext!!, str, android.widget.Toast.LENGTH_SHORT).show()
    }

    inner class ListAdapter(var objectany: Any) : CommonAdapter(objectany) {
        override fun bindData(holder: CommonViewHolder, position: Int) {
            bindAdapterData(holder, position)
        }

        override fun setAdapterLayoutId(): Int {
            return layoutId
        }

        override fun itemClick(view: View?, positon: Int) {
            this@CommonListActivity.itemClick(view, positon)
        }

        override fun itemLongClick(view: View?, positon: Int) {
            super.itemLongClick(view, positon)
            this@CommonListActivity.itemLongClick(view, positon)
        }
    }

    override fun isImmersion(): Boolean {
        return super.isImmersion()
    }

}