package com.allens.model_http.subscriber

import com.allens.model_http.impl.OnHttpListener
import com.allens.model_http.manager.HttpManager
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody

class BeanObserver<T>(private val tClass: Class<T>, private val listener: OnHttpListener<T>) :
    BaseObserver<ResponseBody>() {

    lateinit var value: ResponseBody
    override fun onNext(value: ResponseBody) {
        super.onNext(value)
        this.value = value
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        listener.onError(e)
    }

    override fun onComplete() {
        super.onComplete()
        Logger.i("=======================complete")
        try {
            val json: String = value.string()
            val t = HttpManager.gson.fromJson(json, tClass)
            listener.onSuccess(t)
        } catch (e: Throwable) {
            listener.onError(e)
        }
    }
}