package com.task.currencyapp.ui.main.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.currencyapp.databinding.LayoutItemRateBinding
import com.task.currencyapp.domain.datadtos.ExchangeRate
import com.task.currencyapp.utils.base.interfaces.OnItemClickListener


class RateListItemViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(
        data: ExchangeRate,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        when (binding) {
            is LayoutItemRateBinding -> {
                binding.tvRate.text = data.exchangeRate.toString()
                binding.tvCurrency.text = data.currencyCode
                binding.cvMain.setOnClickListener {
                    onItemClickListener?.onItemClick(it, data, position)
                }
            }
        }
    }
}