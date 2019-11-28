package com.allens.impl

interface DownLoadProgressListener {
    /**
     * 下载进度
     *
     * @param key url
     * @param read  读取
     * @param count 总共长度
     * @param done  是否完成
     */
    fun update(
        key: String,
        read: Long,
        count: Long,
        done: Boolean
    )
}

abstract class OnDownLoadListener : DownLoadProgressListener {

    //进度
    override fun update(key: String, read: Long, count: Long, done: Boolean) {
    }

    //进度
    fun onProgress(key: String, progress: Int) {}

    //失败
    abstract fun onError(key: String, throwable: Throwable)

    //成功
    abstract fun onSuccess(key: String, path: String)

    //暂停
    fun onPause(key: String) {}

    //cancel
    fun onCancel(key: String) {}
}