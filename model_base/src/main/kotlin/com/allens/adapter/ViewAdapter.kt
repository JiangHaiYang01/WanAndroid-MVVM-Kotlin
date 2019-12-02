package com.allens.adapter

import android.R
import android.R.attr
import android.R.attr.checked
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
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

//图片选择器
@BindingAdapter(value = ["app:state_checked_false", "app:state_checked_true"], requireAll = false)
fun View.addCheckedStatus(state_checked_false: Drawable?, state_checked_true: Drawable?) {
    val listDrawable = StateListDrawable()
    //选中
    if (state_checked_true != null) {
        listDrawable.addState(intArrayOf(R.attr.state_checked), state_checked_true)
    }
    //未选中
    if (state_checked_false != null) {
        listDrawable.addState(intArrayOf(-R.attr.state_checked), state_checked_false)
    }

    setBackground(listDrawable)

}

