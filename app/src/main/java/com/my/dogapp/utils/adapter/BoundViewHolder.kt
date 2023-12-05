package com.my.dogapp.utils.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BoundViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: RecyclerListItem)
    abstract fun unbind()

    abstract class ViewHolderFactory(
        val viewType: ViewType
    ) {
        abstract fun createViewHolder(parent: ViewGroup): BoundViewHolder
    }
}
