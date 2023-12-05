package com.my.dogapp.viewholderfactory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.my.dogapp.R
import com.my.dogapp.databinding.ItemBreedBinding
import com.my.dogapp.model.BreedDto
import com.my.dogapp.utils.adapter.BoundViewHolder
import com.my.dogapp.utils.adapter.DataBoundViewHolder
import com.my.dogapp.utils.adapter.RecyclerListItem


class BreedItemViewHolderFactory(
    val listener: RecyclerListItem.ClickListener,
) : BoundViewHolder.ViewHolderFactory(
    viewType = BreedDto::class.qualifiedName.hashCode()
) {
    override fun createViewHolder(parent: ViewGroup): BoundViewHolder {
        val breedItemBinding: ItemBreedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_breed,
            parent,
            false,
            DataBindingUtil.getDefaultComponent()
        )

        return object : DataBoundViewHolder<ItemBreedBinding>(breedItemBinding) {
            override fun setData(binding: ItemBreedBinding, data: RecyclerListItem?) {
                if (data !is BreedDto) {
                    return
                }
                binding.breed = data
                binding.itemBreed.setOnClickListener {
                    listener.onClickRecyclerItem(data)
                }
            }
        }
    }
}