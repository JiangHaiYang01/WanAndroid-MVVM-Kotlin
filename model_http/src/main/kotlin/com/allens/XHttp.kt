package com.allens.model_http

import androidx.annotation.MainThread
import com.allens.model_http.config.HttpConfig
import com.allens.model_http.config.HttpLevel
import com.allens.model_http.impl.ApiService
import com.allens.model_http.impl.OnHttpListener
import com.allens.model_http.impl.OnLogFilterListener
import com.allens.model_http.manager.HttpManager
import com.allens.model_http.subscriber.BeanObserver
import com.allens.model_http.tools.ObservableTool
import com.allens.model_http.tools.UrlTool
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 11:48
 * @Version:        1.0
 */
class XHttp {


    class Builder {
        fun writeTimeout(time: Long): Builder {
            HttpConfig.writeTime = time
            return this
        }

        fun readTimeout(time: Long): Builder {
            HttpConfig.readTime = time
            return this
        }

        fun connectTimeout(time: Long): Builder {
            HttpConfig.connectTime = time
            return this
        }

        fun retryOnConnectionFailure(retryOnConnectionFailure: Boolean): Builder {
            HttpConfig.retryOnConnectionFailure = retryOnConnectionFailure
            return this
        }

        fun isLog(isLog: Boolean): Builder {
            HttpConfig.isLog = isLog
            return this
        }

        fun level(level: HttpLevel): Builder {
            HttpConfig.level = level
            return this
        }

        fun addLogFilter(listener: OnLogFilterListener?): Builder {
            HttpConfig.logFilterListener = listener
            return this
        }

        fun addHead(map: Map<String, String>?): Builder {
            HttpConfig.heardMap = map
            return this
        }

        fun baseUrl(url: String): Builder {
            HttpConfig.baseUrl = url
            return this
        }

        fun build(): XHttp {
            HttpManager.build()
            return XHttp()
        }
    }

    //==============================================================================================
    // 请求方法
    //==============================================================================================
    fun <T> doGet(
        tClass: Class<T>,
        parameter: String,
        listener: OnHttpListener<T>
    ) {
        ObservableTool.getObservableGet(parameter, listener)
            .subscribe(BeanObserver(tClass, listener))
    }


    fun <T> doPost(
        tClass: Class<T>,
        parameter: String,
        listener: OnHttpListener<T>
    ) {
        ObservableTool.getObservablePost(parameter, listener)
            .subscribe(BeanObserver(tClass, listener))
    }


    fun <T> doBody(
        tClass: Class<T>,
        parameter: String,
        listener: OnHttpListener<T>
    ) {
        ObservableTool.getObservableBody(parameter, listener)
            .subscribe(BeanObserver(tClass, listener))
    }

    fun <T> doPut(
        tClass: Class<T>,
        parameter: String,
        listener: OnHttpListener<T>
    ) {
        ObservableTool.getObservablePut(parameter, listener)
            .subscribe(BeanObserver(tClass, listener))
    }

    fun <T> doDelete(
        tClass: Class<T>,
        parameter: String,
        listener: OnHttpListener<T>
    ) {
        ObservableTool.getObservableDelete(parameter, listener)
            .subscribe(BeanObserver(tClass, listener))
    }






}

