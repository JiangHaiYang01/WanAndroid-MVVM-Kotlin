package com.allens.model_http.manager

import com.allens.model_http.XHttp
import com.allens.model_http.config.HttpConfig
import com.allens.model_http.interceptor.HeardInterceptor
import com.allens.model_http.interceptor.LogInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 11:52
 * @Version:        1.0
 */
object HttpManager {



    init {
        createBuilder()
    }

    fun build(): HttpManager {
        buildOkHttp()
        retrofit = createRetrofit()
        return this
    }


    private lateinit var okHttpBuilder: OkHttpClient.Builder
    private lateinit var retrofitBuilder: Retrofit.Builder
    lateinit var retrofit: Retrofit
    val gson: Gson = Gson()

    private fun createBuilder() {
        okHttpBuilder = OkHttpClient.Builder()
        retrofitBuilder = Retrofit.Builder()
    }

    private fun buildOkHttp() {
        okHttpBuilder.connectTimeout(HttpConfig.connectTime, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(HttpConfig.readTime, TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(HttpConfig.writeTime, TimeUnit.SECONDS)
        okHttpBuilder.retryOnConnectionFailure(HttpConfig.retryOnConnectionFailure)
        if (HttpConfig.isLog)
            okHttpBuilder.addInterceptor(LogInterceptor.register(HttpConfig.level))
        val map = HttpConfig.heardMap
        if (map != null && map.isNullOrEmpty()) {
            okHttpBuilder.addInterceptor(HeardInterceptor.register(map))
        }
    }

    private fun createRetrofit(): Retrofit {
        return retrofitBuilder
            .client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())         // json 解析器
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // 支持RxJava
            .baseUrl(HttpConfig.baseUrl)
            .build()
    }


    fun <T> getService(tClass: Class<T>): T {
        val t = retrofit.create(tClass)
        retrofit = createRetrofit()
        return t
    }
}