package com.my.dogapp.viewholderfactory

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.my.dogapp.R
import com.my.dogapp.databinding.ItemFilterBinding
import com.my.dogapp.model.Filter
import com.my.dogapp.utils.adapter.BoundViewHolder
import com.my.dogapp.utils.adapter.DataBoundViewHolder
import com.my.dogapp.utils.adapter.RecyclerListItem


class FilterItemViewHolderFactory(
    val listener: RecyclerListItem.ClickListener,
) : BoundViewHolder.ViewHolderFactory(
    viewType = Filter::class.qualifiedName.hashCode()
) {
    override fun createViewHolder(parent: ViewGroup): BoundViewHolder {
        val filterItemBinding: ItemFilterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_filter,
            parent,
            false,
            DataBindingUtil.getDefaultComponent()
        )

        return object : DataBoundViewHolder<ItemFilterBinding>(filterItemBinding) {
            @SuppressLint("ClickableViewAccessibility")
            override fun setData(binding: ItemFilterBinding, data: RecyclerListItem?) {
                if (data !is Filter) {
                    return
                }
                binding.filter = data
                binding.itemFilter.setOnClickListener {
                    listener.onClickRecyclerItem(data)
                }
            }
        }
    }
}