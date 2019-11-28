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


@BindingAdapter("app:imgSrc")
fun ImageView.imgSrc(resId: Int) {
    setImageResource(resId)
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
    error: Int?,
    placeholderRes: Int?,
    isCircleCrop: Boolean = false,
    isRounded: Boolean = false,
    roundingRadius: Int = 10
) {
    val builder = Glide.with(context).load(url)
    println("自定义的image adapter ---------------------------------------start")

    //错误显示图片
    if (error != null) {
        println("自定义的image adapter ---------------------------------------错误显示图片")
        builder.error(error)
    }
    //占位图
    if (placeholderRes != null) {
        println("自定义的image adapter ---------------------------------------占位图")
        builder.placeholder(placeholderRes)
    }

    //圆角半径
    if (isRounded && roundingRadius > 0) {
        println("自定义的image adapter ---------------------------------------圆角半径")
        builder.apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
    } else if (isRounded) {
        println("自定义的image adapter ---------------------------------------圆角半径 roundingRadius must >0")
    }
    //圆形
    if (isCircleCrop) {
        println("自定义的image adapter ---------------------------------------圆形")
        builder.apply(RequestOptions.bitmapTransform(CircleCrop()))
    }


    builder.into(this)
}