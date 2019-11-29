package com.allens.status

import androidx.lifecycle.MutableLiveData
import com.allens.LogHelper
import com.allens.config.SpConfig
import com.tencent.mmkv.MMKV


//用户状态
object UserStatus {

    //是否登录
    var isLogIn =
        MutableLiveData<Boolean>().apply { value = MMKV.defaultMMKV().decodeBool(SpConfig.isLogin) }

    //用户token
    var token = MutableLiveData<String>().apply {
        value = MMKV.defaultMMKV().decodeString(SpConfig.userToken)
    }
    //用户userId
    var userId =
        MutableLiveData<Int>().apply { value = MMKV.defaultMMKV().decodeInt(SpConfig.userId) }
    //手机号
    var userPhone = MutableLiveData<String>().apply {
        value = MMKV.defaultMMKV().decodeString(SpConfig.userPhone)
    }
    //头像
    var icon =
        MutableLiveData<String>().apply { value = MMKV.defaultMMKV().decodeString(SpConfig.icon) }


}