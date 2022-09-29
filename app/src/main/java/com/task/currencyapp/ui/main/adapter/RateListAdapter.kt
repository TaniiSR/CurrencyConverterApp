package com.task.currencyapp.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.task.currencyapp.R
import com.task.currencyapp.databinding.LayoutItemRateBinding
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.utils.base.BaseRecyclerAdapter

class RateListAdapter(
    private val list: MutableList<ExchangeRate>,
) : BaseRecyclerAdapter<ExchangeRate, RateListItemViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewBinding): RateListItemViewHolder {
        return RateListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateListItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], position, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_item_rate
    }

    override fun getItemCount(): Int = list.size

    override fun getViewBindingByViewType(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return LayoutItemRateBinding.inflate(layoutInflater, viewGroup, false)
    }

    fun updateRateListItems(rateList: List<ExchangeRate>) {
        val diffCallback = RateDiffCallback(list, rateList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffCallback)
        this.setList(rateList)
        diffResult.dispatchUpdatesTo(this)
    }
}