package com.my.dogapp.utils.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class BoundListAdapter(
    private val viewHolderFactoryMap: Map<ViewType, BoundViewHolder.ViewHolderFactory>
) : ListAdapter<RecyclerListItem, BoundViewHolder>(DiffCallBack) {
    constructor(viewHolderFactoryList: List<BoundViewHolder.ViewHolderFactory>) : this(
        viewHolderFactoryList.associateBy { it.viewType }
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: ViewType): BoundViewHolder {
        return viewHolderFactoryMap[viewType]?.createViewHolder(parent)
            ?: throw UnsupportedOperationException("Trying to get missing ViewHolderFactory, viewType:$viewType")
    }

    override fun getItemViewType(position: Int): ViewType {
        return currentList.getOrNull(position)?.viewType()
            ?: throw UnsupportedOperationException("Trying to get missing view type, position:$position")
    }

    override fun onBindViewHolder(holder: BoundViewHolder, position: Int) {
        currentList.getOrNull(position)?.let(holder::bind)
            ?: throw UnsupportedOperationException(
                "Trying to bind missing RecyclerListItem, " +
                        "position:${position}, " +
                        "holder:${holder::class.qualifiedName}"
            )
    }

    private object DiffCallBack : DiffUtil.ItemCallback<RecyclerListItem>() {
        override fun areItemsTheSame(oldItem: RecyclerListItem, newItem: RecyclerListItem): Boolean =
            oldItem.isSameItemAs(newItem)

        override fun areContentsTheSame(oldItem: RecyclerListItem, newItem: RecyclerListItem): Boolean =
            oldItem.isSameContentAs(newItem)
    }
}
