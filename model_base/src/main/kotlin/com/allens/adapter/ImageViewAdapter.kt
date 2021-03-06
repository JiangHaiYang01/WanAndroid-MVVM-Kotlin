package com.allens.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.media.Image
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("app:imgSrc",requireAll = false)
fun ImageView.imgSrc(resId: Drawable) {
    setImageDrawable(resId)
}

//使用拓展方法
//这里有个坑,不能写在class 中
//或者就使用静态类去写
@SuppressLint("CheckResult")
@BindingAdapter(
    value = ["app:imgUrl", "app:imgError", "app:imgPlace",
        "app:imgIsCircleCrop", "app:imgIsRounded", "app:imgIsRoundingRadius"],
    requireAll = false
)
fun ImageView.loadImg(
    url: String?,
    error: Drawable?,
    placeholderRes: Drawable?,
    isCircleCrop: Boolean = false,
    isRounded: Boolean = false,
    roundingRadius: Int = 10
) {
    val builder = Glide.with(context).load(url)

    //错误显示图片
    if (error != null) {
        builder.error(error)
    }
    //占位图
    if (placeholderRes != null) {
        builder.placeholder(placeholderRes)
    }

    //圆角半径
    if (isRounded && roundingRadius > 0) {
        builder.apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
    } else if (isRounded) {
        println("自定义的image adapter ---------------------------------------圆角半径 roundingRadius must >0")
    }
    //圆形
    if (isCircleCrop) {
        builder.apply(RequestOptions.bitmapTransform(CircleCrop()))
    }


    builder.into(this)
}