package com.allens.adapter

import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ImageViewAdapter {
    companion object {
        @BindingAdapter(value = ["imgSrc"], requireAll = false)
        @JvmStatic
        fun src(imageView: ImageView, resId: Int) {
            imageView.setImageResource(resId)
        }

        @BindingAdapter(value = ["imgUrl", "imgError", "imgPlace"], requireAll = false)
        @JvmStatic
        fun loadImg(
            imageView: ImageView,
            url: String?,
            error: Drawable?,
            placeholderRes: Drawable?
        ) {
            Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions().placeholder(placeholderRes).error(error))
                .into(imageView)
        }
    }
}

