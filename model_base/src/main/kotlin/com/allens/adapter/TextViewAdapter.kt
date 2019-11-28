package com.allens.adapter

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter(value = ["app:tvStyle"], requireAll = false)
fun TextView.setStyle(resId: Int) {
    setTextAppearance(context, resId)
}