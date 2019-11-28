package com.allens.subscriber

import com.allens.impl.DownLoadProgressListener
import com.allens.impl.OnDownLoadListener
import com.allens.model_http.subscriber.BaseObserver
import io.reactivex.disposables.Disposable

class DownLoadObserver(
    private val key: String,
    private val listener: OnDownLoadListener
) : BaseObserver<DownLoadBean>() {


    private lateinit var downLoadBean: DownLoadBean
    override fun onSubscribe(d: Disposable) {
        super.onSubscribe(d)
    }

    override fun onComplete() {
        super.onComplete()
        if (downLoadBean.isSuccess) {
            listener.onSuccess(key, downLoadBean.path)
        } else {
            listener.onError(key, downLoadBean.throwable)
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        listener.onError(key, e)

    }

    override fun onNext(value: DownLoadBean) {
        super.onNext(value)
        downLoadBean = value
    }
}


data class DownLoadBean(
    val throwable: Throwable,
    val isSuccess: Boolean,
    val path: String
)




