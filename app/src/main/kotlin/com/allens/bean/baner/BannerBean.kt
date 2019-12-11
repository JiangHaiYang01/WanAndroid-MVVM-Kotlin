package com.allens.bean.baner

data class BannerBean(
    val `data`: List<Data>,
    val errorCode: Int,
    val errorMsg: String
)