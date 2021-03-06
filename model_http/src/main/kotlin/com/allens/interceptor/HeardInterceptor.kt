package com.allens.interceptor

import com.allens.model_http.XHttp
import com.allens.model_http.config.HttpConfig
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Request

//请求头
object HeardInterceptor {
    fun register(map: Map<String, String>): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val builder: Request.Builder = request.newBuilder()
            for ((key, value) in map.entries) {
                if (HttpConfig.isLog) {
                    Logger.i("http----> add heard [key]:$key [value]:$value ")
                    builder.addHeader(key, value)
                }
            }
            chain.proceed(builder.build())
        }
    }
}

