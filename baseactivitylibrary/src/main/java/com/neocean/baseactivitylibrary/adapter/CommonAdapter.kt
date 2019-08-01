package com.neocean.app.myapplication.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neocean.baseactivitylibrary.R
import java.lang.Exception
import kotlin.collections.ArrayList


/**
 * User weixn
 * Date 2019/7/3
 */
abstract class CommonAdapter(var any: Any) : RecyclerView.Adapter<CommonAdapter.CommonViewHolder>() {
    private var adapterCount: Int = 0
    lateinit var adapterContext: Context

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        adapterContext = parent.context
        var view = LayoutInflater.from(adapterContext).inflate(setAdapterLayoutId(), parent, false)
        view.id = R.id.root_id
        return CommonViewHolder(view, adapterContext)
    }

    final override fun getItemCount(): Int {
        when (any) {
            is ArrayList<*> -> {
                //TODO list 集合
                return (any as ArrayList<*>).size
            }
            is Array<*> -> {
                //TODO 数组
                return (any as Array<*>).size
            }
            else -> {
                throw Exception("不允许数据类型:" + any?.javaClass)
            }
        }
        return 0

    }

    final override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        bindData(holder, position)
        holder.map[R.id.root_id]?.setOnClickListener { itemClick(holder.map[R.id.root_id], position) }
        holder.map[R.id.root_id]?.setOnLongClickListener {
            itemLongClick(holder.map[R.id.root_id], position)
            true
        }
    }

    open fun itemClick(view: View?, positon: Int) {}
    open fun itemLongClick(view: View?, positon: Int) {}


    abstract fun setAdapterLayoutId(): Int
    abstract fun bindData(holder: CommonViewHolder, position: Int)

    class CommonViewHolder(private val item: View, private val adapterContext: Context) :
        RecyclerView.ViewHolder(item) {

        val map = HashMap<Int, View>()

        init {

            var viewGroup: ViewGroup = item as ViewGroup
            Log.e("xxx", "${getResourceEntryName(viewGroup.id)}")
            if (viewGroup.id != -1)
                map[viewGroup.id] = getView(viewGroup.id)
            for (psotion in 0 until viewGroup.childCount) {
                when (viewGroup.getChildAt(psotion).id != -1) {
                    true -> {
                        map[viewGroup.getChildAt(psotion).id] = getView(viewGroup.getChildAt(psotion).id)
                    }
                }
            }
        }

        private fun getView(id: Int): View {
            var view: View = item.findViewById(id)

            return view as View
        }


        fun getResourceEntryName(id: Int): String? {
            //-1未设定id
            when (id != -1) {
                true -> {
                    return adapterContext.resources.getResourceEntryName(id)
                }
            }
            return null
        }
    }

}