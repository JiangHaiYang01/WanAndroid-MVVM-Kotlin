package com.allens.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoaderInterface

class BannerAdapter : ImageLoaderInterface<ImageView> {
    override fun createImageView(context: Context?): ImageView {
        return ImageView(context)
    }

    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (context != null) {
            if (imageView != null) {
                Glide.with(context).load(path).into(imageView)
            }
        }
    }

}

