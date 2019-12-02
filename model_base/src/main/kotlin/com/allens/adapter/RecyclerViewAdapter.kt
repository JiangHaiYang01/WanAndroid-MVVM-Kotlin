package com.allens.adapter

import android.graphics.drawable.Drawable
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

//添加自定义分割线
@BindingAdapter(value = ["app:recyclerViewLine", "app:recyclerViewLineDef"], requireAll = false)
fun RecyclerView.addItemDecoration(
    drawable: Drawable?,
    drawableDef: Boolean?
) {

    when {
        //自定义的不是空 就用自定义的
        drawable != null -> {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            divider.setDrawable(drawable)
            addItemDecoration(divider)
        }
        //默认的并不是空 就用默认的
        drawableDef != null && drawableDef == true -> {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}