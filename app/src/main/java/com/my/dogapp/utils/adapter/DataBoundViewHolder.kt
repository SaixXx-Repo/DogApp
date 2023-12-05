package com.my.dogapp.utils.adapter

import androidx.databinding.ViewDataBinding

abstract class DataBoundViewHolder<V : ViewDataBinding>(
    private val binding: V
) : BoundViewHolder(binding.root) {
    override fun bind(data: RecyclerListItem) {
        setData(binding, data)
        binding.executePendingBindings()
    }

    override fun unbind() {
        setData(binding, null)
        binding.unbind()
        binding.executePendingBindings()
    }

    abstract fun setData(binding: V, data: RecyclerListItem?)
}
