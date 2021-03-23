package com.example.gweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gweather.BR
import com.example.gweather.R

/**
 *作者:created by HP on 2021/3/8 17:23
 *邮箱:sakurajimamai2020@qq.com
 */
class MyBindingAdapter(private var items: MutableList<BindingAdapterItem>) :
    RecyclerView.Adapter<MyBindingAdapter.BindingHolder>() {

    private var itemClick: ItemClick? = null

    fun setItemClick(itemClick: ItemClick) {
        this.itemClick = itemClick
    }

    /*
    * 数据绑定
    * */
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.bindData(items[position])
        holder.itemView.findViewById<LinearLayout>(R.id.rootView).setOnClickListener {
            itemClick?.onItemClickListener(position)
        }
    }

    interface ItemClick {
        fun onItemClickListener(position: Int)
    }

    /**
     * @return 返回的是adapter的view
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return BindingHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getViewType()

    class BindingHolder(private var binding: ViewDataBinding) : ViewHolder(binding.root) {
        fun bindData(item: BindingAdapterItem?) {
            binding.setVariable(BR.item, item)
        }
    }
}