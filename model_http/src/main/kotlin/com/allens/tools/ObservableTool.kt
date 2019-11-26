package com.allens.model_http.tools

import com.allens.model_http.impl.ApiService
import com.allens.model_http.impl.OnHttpListener
import com.allens.model_http.manager.HttpManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.util.*

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 16:07
 * @Version:        1.0
 */
object ObservableTool {

    fun <T> getObservableGet(
        parameter: String,
        listener: OnHttpListener<T>
    ): Observable<ResponseBody> {
        val baseUrl = HttpManager.retrofit.baseUrl().toUrl().toString()
        val heard = HashMap<String, String>()
        listener.onHeard(heard)
        val map = HashMap<String, Any>()
        listener.onMap(map)
        var getUrl: String = baseUrl + parameter
        if (map.size > 0) {
            val param: String = UrlTool.prepareParam(map)
            if (param.trim().isNotEmpty()) {
                getUrl += "?$param"
            }
        }
        return HttpManager.getService(ApiService::class.java)
            .doGet(heard, getUrl)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun <T> getObservablePost(
        parameter: String,
        listener: OnHttpListener<T>
    ): Observable<ResponseBody> {
        val heard = HashMap<String, String>()
        listener.onHeard(heard)
        val map = HashMap<String, Any>()
        listener.onMap(map)
        return HttpManager.getService(ApiService::class.java)
            .doPost(parameter, heard, map)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun <T> getObservableBody(
        parameter: String,
        listener: OnHttpListener<T>
    ): Observable<ResponseBody> {
        val heard = HashMap<String, String>()
        listener.onHeard(heard)
        val map = HashMap<String, Any>()
        listener.onMap(map)
        val toJson = HttpManager.gson.toJson(map)
        val requestBody =
            toJson.toRequestBody("application/json".toMediaTypeOrNull())

        return HttpManager.getService(ApiService::class.java)
            .doBody(parameter, heard, requestBody)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun <T> getObservablePut(
        parameter: String,
        listener: OnHttpListener<T>
    ): Observable<ResponseBody> {
        val heard = HashMap<String, String>()
        listener.onHeard(heard)
        val map = HashMap<String, Any>()
        listener.onMap(map)
        return HttpManager.getService(ApiService::class.java)
            .doPut(parameter, heard, map)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun <T> getObservableDelete(
        parameter: String,
        listener: OnHttpListener<T>
    ): Observable<ResponseBody> {
        val heard = HashMap<String, String>()
        listener.onHeard(heard)
        val map = HashMap<String, Any>()
        listener.onMap(map)
        return HttpManager.getService(ApiService::class.java)
            .doDelete(parameter, heard, map)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun getObservableUpload(
        url: String,
        heard: HashMap<String, String>,
        requestBody: RequestBody
    ): Observable<ResponseBody> {
        return HttpManager.getService(ApiService::class.java)
            .upload(url, heard, requestBody)
            .subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}