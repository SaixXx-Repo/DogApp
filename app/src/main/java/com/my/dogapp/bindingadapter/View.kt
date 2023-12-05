package com.my.dogapp.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean?) {
    visible ?: return
    view.visibility = when {
        visible -> View.VISIBLE
        else -> View.GONE
    }
}
