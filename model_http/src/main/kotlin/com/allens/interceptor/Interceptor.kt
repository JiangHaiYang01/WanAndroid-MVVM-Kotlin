package com.allens.model_http.interceptor

import com.allens.model_http.XHttp
import com.allens.model_http.config.HttpConfig
import com.allens.model_http.config.HttpLevel
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 14:02
 * @Version:        1.0
 */

//日志拦截器
object LogInterceptor {
    fun register(level: HttpLevel): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (HttpConfig.logFilterListener != null && HttpConfig.logFilterListener!!.filter(
                        message
                    )
                ) {
                    return
                }
                Logger.i(message)
            }
        })
        interceptor.level = HttpLevel.conversion(level)
        return interceptor
    }
}

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




