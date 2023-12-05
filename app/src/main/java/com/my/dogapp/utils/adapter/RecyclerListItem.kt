package com.my.dogapp.utils.adapter

typealias ViewType = Int

interface RecyclerListItem {
    fun isSameItemAs(otherItem: RecyclerListItem): Boolean = this == otherItem
    fun isSameContentAs(otherItem: RecyclerListItem): Boolean = this == otherItem

    fun viewType(): ViewType = this::class.qualifiedName.hashCode()

    interface ClickListener {
        fun onClickRecyclerItem(item: RecyclerListItem, clickType: ClickType = ClickType.ListItemClicked)
    }
}

/**
 * This is a common recycler item click type, developers to define new types based on the requirements
 * on different screens and handle only those types and ignore other types.
 */
open class ClickType {
    object ListItemClicked : ClickType()
}
