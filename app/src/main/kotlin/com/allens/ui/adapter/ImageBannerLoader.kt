package com.allens.ui.adapter

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader
import com.youth.banner.loader.ImageLoaderInterface

class ImageBannerLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        if (imageView != null) {
            if (context != null) {
                Glide.with(context).load(path as String).into(imageView)
            }
        };
    }

}
