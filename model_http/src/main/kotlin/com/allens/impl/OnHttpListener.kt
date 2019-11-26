package com.allens.model_http.impl

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-22 11:49
 * @Version:        1.0
 */


interface OnHttpListener<T> {
    //请求头
    fun onHeard(map: Map<String, String>)

    //请求体
    fun onMap(map: Map<String, Any>)

    //成功
    fun onSuccess(t: T)

    //失败
    fun onError(e: Throwable)
}