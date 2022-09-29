package com.task.currencyapp.utils.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.currencyapp.utils.base.interfaces.OnItemClickListener


abstract class BaseRecyclerAdapter<T : Any, VH : RecyclerView.ViewHolder>() :
    RecyclerView.Adapter<VH>() {

    var onItemClickListener: OnItemClickListener? = null
    var allowFullItemClickListener: Boolean = false
    private lateinit var list: MutableList<T>

    constructor(list: MutableList<T>) : this() {
        this.list = list
    }

    open fun onCreateViewHolder(binding: ViewBinding, viewType: Int): VH {
        return onCreateViewHolder(binding)
    }

    protected abstract fun onCreateViewHolder(binding: ViewBinding): VH

    protected abstract fun getViewBindingByViewType(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = getViewBindingByViewType(layoutInflater, parent, viewType)
        return onCreateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (allowFullItemClickListener)
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(
                    it,
                    getDataForPosition(position),
                    position
                )
            }
    }

    fun getDataForPosition(position: Int): T {
        //if (position > list.size - 1)
        return list[position]
    }

    fun getDataList(): MutableList<T> {
        return list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItemAt(position: Int) {
        this.list.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeAllItems() {
        this.list.clear()
        notifyDataSetChanged()
    }

    fun setItemListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

}