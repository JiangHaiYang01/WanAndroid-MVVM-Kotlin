package com.allens.status

import androidx.lifecycle.MutableLiveData
import com.allens.bean.login.LogInBean
import com.allens.bean.logout.LogOutBean
import com.allens.bean.user_detail.UserDetailBean
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
    //cookie
    var cookie =
        MutableLiveData<String>().apply { value = MMKV.defaultMMKV().decodeString(SpConfig.cookie) }
    //总积分
    var coinCount =
        MutableLiveData<Int>().apply { value = MMKV.defaultMMKV().decodeInt(SpConfig.coinCount) }
    // 等级
    var level =
        MutableLiveData<Int>().apply { value = MMKV.defaultMMKV().decodeInt(SpConfig.level) }
    // 当前排名
    var rank =
        MutableLiveData<Int>().apply { value = MMKV.defaultMMKV().decodeInt(SpConfig.rank) }


    fun login(t: LogInBean) {
        //保存登录状态
        MMKV.defaultMMKV().encode(SpConfig.isLogin, true)
        MMKV.defaultMMKV().encode(SpConfig.userPhone, t.data.nickname)
        MMKV.defaultMMKV().encode(SpConfig.userToken, t.data.token)
        MMKV.defaultMMKV().encode(SpConfig.userId, t.data.id)
        MMKV.defaultMMKV().encode(SpConfig.icon, t.data.icon)


        //通知状态变化
        isLogIn.value = true
        userPhone.value = t.data.nickname
        token.value = t.data.token
        userId.value = t.data.id
        icon.value = t.data.icon
    }

    fun logOut(t: LogOutBean) {
        MMKV.defaultMMKV().clearAll()


        //通知状态变化
        isLogIn.value = false
        userPhone.value = null
        token.value = null
        userId.value = null
        icon.value = null
    }

    fun setUserRank(t: UserDetailBean) {
        MMKV.defaultMMKV().encode(SpConfig.coinCount, t.data.coinCount)
        MMKV.defaultMMKV().encode(SpConfig.level, t.data.level)
        MMKV.defaultMMKV().encode(SpConfig.rank, t.data.rank)

        coinCount.value = t.data.coinCount
        level.value = t.data.level
        rank.value = t.data.rank
    }
}