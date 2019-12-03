package com.allens.model_http.config

import com.allens.impl.OnCookieInterceptor
import com.allens.interceptor.OnCookieListener
import com.allens.model_http.impl.OnLogFilterListener
import okhttp3.logging.HttpLoggingInterceptor

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 12:01
 * @Version:        1.0
 */

open class HttpConfig {
    companion object {
        var connectTime: Long = 10
        var readTime: Long = 10
        var writeTime: Long = 10
        var retryOnConnectionFailure: Boolean = false
        var isLog: Boolean = true
        var level: HttpLevel = HttpLevel.BODY
        var baseUrl: String = "http://badu.com/"
        var logFilterListener: OnLogFilterListener? = null
        var cookieListener: OnCookieListener? = null
        var onCookieInterceptor: OnCookieInterceptor? = null
        var heardMap: Map<String, String>? = null
    }
}

enum class HttpLevel {
    NONE,
    BASIC,
    HEADERS,
    BODY;

    companion object {
        fun conversion(level: HttpLevel): HttpLoggingInterceptor.Level {
            return when (level) {
                BODY -> HttpLoggingInterceptor.Level.BODY
                NONE -> HttpLoggingInterceptor.Level.NONE
                BASIC -> HttpLoggingInterceptor.Level.BASIC
                HEADERS -> HttpLoggingInterceptor.Level.HEADERS
                else -> HttpLoggingInterceptor.Level.BODY
            }
        }
    }
}