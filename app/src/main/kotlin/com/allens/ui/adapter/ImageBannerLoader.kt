package com.allens.ui.adapter

import android.content.Context
import android.widget.ImageView
import com.allens.bean.BannerBean
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

class ImageBannerLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (imageView != null) {
            if (context != null) {
                Glide.with(context).load(path as String).into(imageView)
            }
        };
    }

}