package com.my.dogapp.viewholderfactory

import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.my.dogapp.R
import com.my.dogapp.databinding.ItemPostBinding
import com.my.dogapp.model.BreedPostDto
import com.my.dogapp.utils.DoubleTapListener
import com.my.dogapp.utils.adapter.BoundViewHolder
import com.my.dogapp.utils.adapter.DataBoundViewHolder
import com.my.dogapp.utils.adapter.RecyclerListItem


class BreedPostItemViewHolderFactory(
    val listener: RecyclerListItem.ClickListener,
) : BoundViewHolder.ViewHolderFactory(
    viewType = BreedPostDto::class.qualifiedName.hashCode()
) {
    override fun createViewHolder(parent: ViewGroup): BoundViewHolder {
        val breedItemBinding: ItemPostBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_post,
            parent,
            false,
            DataBindingUtil.getDefaultComponent()
        )

        return object : DataBoundViewHolder<ItemPostBinding>(breedItemBinding) {
            @SuppressLint("ClickableViewAccessibility")
            override fun setData(binding: ItemPostBinding, data: RecyclerListItem?) {
                if (data !is BreedPostDto) {
                    return
                }
                binding.post = data
                binding.ivAddToFavourite.setOnClickListener {
                    listener.onClickRecyclerItem(data)
                }
                val doubleTapListener = DoubleTapListener(binding.itemPost.context) {
                    listener.onClickRecyclerItem(data)
                }
                val gestureDetector = GestureDetector(binding.itemPost.context, doubleTapListener)
                binding.itemPost.setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                }
            }
        }
    }
}