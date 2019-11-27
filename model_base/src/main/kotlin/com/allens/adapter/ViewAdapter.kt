package com.allens.adapter

import android.view.View
import androidx.databinding.BindingAdapter


//view 是否显示
@BindingAdapter(value = ["app:isVisible"], requireAll = false)
fun View.isVisible(visibility: Boolean) {
    if (visibility) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}