package com.allens.bean

data class BannerBean(
    val `data`: List<BannerResultBean>,
    val errorCode: Int,
    val errorMsg: String
)

data class BannerResultBean(
    val desc: String,
    val id: Int,
    val imagePath: String,
    val isVisible: Int,
    val order: Int,
    val title: String,
    val type: Int,
    val url: String
)