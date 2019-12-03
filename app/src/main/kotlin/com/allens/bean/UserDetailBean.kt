package com.allens.bean

data class UserDetailBean(
    val `data`: UserDetailResultBean,
    val errorCode: Int,
    val errorMsg: String
)

data class UserDetailResultBean(
    val coinCount: Int,
    val level: Int,
    val rank: Int,
    val userId: Int,
    val username: String
)
