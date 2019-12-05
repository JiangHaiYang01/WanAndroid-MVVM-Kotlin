package com.allens.interceptor

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
                Logger.i("add request heard $key  $value")
                builder.addHeader(key, value)
            }
            chain.proceed(builder.build())
        }
    }
}

