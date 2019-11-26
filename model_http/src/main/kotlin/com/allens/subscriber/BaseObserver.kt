package com.allens.model_http.subscriber

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<T> {
    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(value: T) {
    }

    override fun onError(e: Throwable) {
    }

}