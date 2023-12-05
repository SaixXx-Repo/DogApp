package com.my.dogapp.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageUrl")
fun setImageUrl(image: ImageView, url: String?) {
    url ?: return

    Glide.with(image.context)
        .load(url)
        .into(image)
}
