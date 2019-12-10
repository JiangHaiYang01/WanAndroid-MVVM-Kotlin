package com.allens.tool

import androidx.lifecycle.Observer
import com.allens.LogHelper
import com.allens.config.Config
import com.allens.config.SpConfig
import com.allens.impl.OnCookieInterceptor
import com.allens.impl.OnHeardListener
import com.allens.interceptor.OnCookieListener
import com.allens.model_http.XHttp
import com.allens.model_http.config.HttpLevel
import com.allens.status.UserStatus
import com.tencent.mmkv.MMKV

object HttpTool {
    var xHttp = XHttp.Builder()
        .baseUrl(Config.baseURL)
        .level(HttpLevel.BODY)
        .isLog(true)
        .retryOnConnectionFailure(false)
        .addCookieInterceptor(object : OnCookieListener {
            override fun onCookies(cookie: HashSet<String>) {
                cookie.forEach {
                    if (it.contains("JSESSIONID")) {
                        LogHelper.i("appCookie -------------》${cookie.toString()}")
                        UserStatus.cookie.postValue(cookie.toString())

                        //保存
                        val encode =
                            MMKV.defaultMMKV().encode(SpConfig.cookie, cookie.toString())
                        LogHelper.i("cookie save $encode")
                    }
                }
            }
        }, object : OnCookieInterceptor {
            override fun isInterceptorAllRequest(): Boolean {
                return false
            }

            override fun isInterceptorRequest(url: String): Boolean {
                if (url == "https://www.wanandroid.com/user/login") {
                    return true
                }
                return false
            }
        })
        .addHead(object : OnHeardListener {
            override fun onHeardMap(map: HashMap<String, String>) {
                val cookie = UserStatus.cookie.value
                LogHelper.i("add heard $cookie")
                if (cookie != null) {
                    map["Cookie"] = cookie
                }
            }
        })
        .build()
}