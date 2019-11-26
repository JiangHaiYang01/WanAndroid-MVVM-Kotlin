package com.allens.model_http.subscriber

import com.allens.model_http.impl.OnHttpListener
import com.allens.model_http.manager.HttpManager
import com.google.gson.Gson
import okhttp3.ResponseBody

class BeanObserver<T>(private val tClass: Class<T>, private val listener: OnHttpListener<T>) :
    BaseObserver<ResponseBody>() {
    override fun onNext(value: ResponseBody) {
        super.onNext(value)
        try {
            val json: String = value.string()
            val t = HttpManager.gson.fromJson(json, tClass)
            listener.onSuccess(t)
        } catch (e: Throwable) {
            listener.onError(e)
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        listener.onError(e)
    }
}