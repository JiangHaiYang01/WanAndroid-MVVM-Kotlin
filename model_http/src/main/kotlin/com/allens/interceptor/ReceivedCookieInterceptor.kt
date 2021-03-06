package com.allens.interceptor

import com.allens.impl.OnCookieInterceptor
import okhttp3.Interceptor
import okhttp3.Response

//cookie 拦截器
class ReceivedCookiesInterceptorImpl(
    val listener: OnCookieListener,
    private val interceptor: OnCookieInterceptor
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        //不是拦截全部
        if (!interceptor.isInterceptorAllRequest()) {
            val url = response.request.url.toUrl().toString()
            if (interceptor.isInterceptorRequest(url)) {
                interceptor(response)
            }
            return response
        }

        //这里获取请求返回的cookie
        interceptor(response)

        return response
    }

    private fun interceptor(response: Response) {
        if (response.headers("Set-Cookie").isNotEmpty()) {
            val cookies = HashSet<String>()
            for (header in response.headers("Set-Cookie")) {
                cookies.add(header)
            }
            listener.onCookies(cookies)
        }
    }
}

object ReceivedCookieInterceptor {
    fun register(listener: OnCookieListener, interceptor: OnCookieInterceptor): Interceptor {
        return ReceivedCookiesInterceptorImpl(listener, interceptor)
    }
}


interface OnCookieListener {
    fun onCookies(cookie: HashSet<String>)
}


