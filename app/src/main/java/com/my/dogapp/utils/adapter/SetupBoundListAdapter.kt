package com.my.dogapp.utils.adapter

import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter(value = ["recyclerListData", "recyclerViewHolderFactories"], requireAll = true)
fun setupBoundListAdapter(
    recyclerView: RecyclerView,
    items: List<RecyclerListItem>?,
    viewHolderFactories: List<BoundViewHolder.ViewHolderFactory>?
) {
    viewHolderFactories ?: return

    val adapter: BoundListAdapter = when (val existingAdapter = recyclerView.adapter) {
        is BoundListAdapter -> existingAdapter
        else -> {
            BoundListAdapter(viewHolderFactories).also {
                it.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
                recyclerView.adapter = it
            }
        }
    }
    val recyclerListItems: List<RecyclerListItem> = items ?: emptyList()
    adapter.submitList(recyclerListItems)
    adapter.notifyDataSetChanged()
}
