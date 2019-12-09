package com.allens.bean.baner

data class BanerBean(
    val `data`: List<Data>,
    val errorCode: Int,
    val errorMsg: String
)