package com.allens.adapter

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


//使用拓展方法
//这里有个坑,不能写在class 中
//或者就使用静态类去写
@BindingAdapter(value = ["imgSrc"], requireAll = false)
fun ImageView.src(resId: Int) {
    this.setImageResource(resId)
}

@BindingAdapter(value = ["imgUrl", "imgError", "imgPlace"], requireAll = false)
fun ImageView.loadImg(
    url: String?,
    error: Drawable?,
    placeholderRes: Drawable?
) {
    Glide.with(context)
        .load(url)
        .apply(RequestOptions().placeholder(placeholderRes).error(error))
        .into(this)
}