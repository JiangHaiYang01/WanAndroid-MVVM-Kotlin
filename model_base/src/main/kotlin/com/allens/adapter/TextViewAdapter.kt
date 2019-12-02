package com.allens.adapter

import android.R
import android.content.res.ColorStateList
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

//颜色选择器
@BindingAdapter(value = ["app:color_checked", "app:color_normal"], requireAll = true)
fun TextView.addColorStatus(color_true: Int?, color_false: Int?) {
    if (color_false != null && color_true != null) {
        val colors = intArrayOf(color_false, color_true)
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(-R.attr.state_checked)
        states[1] = intArrayOf(R.attr.state_checked)
        val stateList = ColorStateList(states, colors)
        setTextColor(stateList)
    }

}