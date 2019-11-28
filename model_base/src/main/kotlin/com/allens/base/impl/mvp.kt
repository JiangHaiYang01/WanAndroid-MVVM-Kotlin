package com.allens.model_base.base.impl

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.allens.model_base.base.BaseActivity
import com.allens.model_base.base.BaseFragment
import com.allens.model_base.tools.BlueAction
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 11:38
 * @Version:        1.0
 */


interface BaseView {
    //是否有网络
    fun isNetworkAvailable(): Boolean

    //获取资源
    fun getStringResId(id: Int): String

    //防抖点击
    fun clickThrottleFirst(view: View, listener: View.OnClickListener?)

    //防抖点击
    fun clickThrottleFirst(
        view: View,
        windowDuration: Long,
        unit: TimeUnit,
        listener: View.OnClickListener
    )

    //跳转
    fun startActivity(clz: Class<*>)

    //跳转
    fun startActivity(clz: Class<*>, bundle: Bundle)

    //跳转
    fun startActivityForResult(
        cls: Class<*>,
        bundle: Bundle,
        requestCode: Int
    )


    //网络变化
    fun onNewWorkChange(
        isMobileConn: Boolean,
        isWifiConn: Boolean
    )

    //gps变化
    fun onGpsChange(isOpen: Boolean)

    //蓝牙变化
    fun onBlueChange(status: BlueAction)

    //app 前后台变化
    fun onAppUIChange(isBack: Boolean)

    //软键盘消失
    fun hideSoftInput()

}


interface BaseModel


interface BasePresenterImpl<M : BaseModel, V : BaseView> : LifecycleObserver {
    // 注册Model层
    fun registerModel(model: M)

    //注册View层
    fun registerView(view: V)

    //获取View
    fun getView(): V?


    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onAny(
        owner: LifecycleOwner?,
        event: Lifecycle.Event?
    )

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

}

interface MvpCallback<M : BaseModel, V : BaseView, P : BasePresenter<M, V>> {
    fun createModel(): M

    fun createView(): V

    fun createPresenter(): P
}


abstract class BasePresenter<M : BaseModel, V : BaseView> : BasePresenterImpl<M, V> {
    private lateinit var model: M

    private var wrf: WeakReference<V>? = null

    override fun registerView(view: V) {
        wrf = WeakReference(view)
    }

    //view 可空
    override fun getView(): V? {
        return wrf?.get()
    }

    override fun registerModel(model: M) {
        this.model = model
    }


    override fun onAny(owner: LifecycleOwner?, event: Lifecycle.Event?) {
    }

    override fun onCreate() {
    }

    override fun onDestroy() {
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }
}


abstract class BaseMvpActivity<M : BaseModel, V : BaseView, P : BasePresenter<M, V>> :
    BaseActivity(), MvpCallback<M, V, P> {
    lateinit var presenter: P
    override fun initListener() {
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
        presenter.registerModel(createModel())
        presenter.registerView(createView())

        initMvpListener()
    }


    abstract fun initMvpListener()
}


abstract class BaseMvpFragment<M : BaseModel, V : BaseView, P : BasePresenter<M, V>> :
    BaseFragment(),
    MvpCallback<M, V, P> {
    lateinit var presenter: P
    override fun initListener() {
        presenter = createPresenter()
        lifecycle.addObserver(presenter)
        presenter.registerModel(createModel())
        presenter.registerView(createView())

        initMvpListener()
    }

    abstract fun initMvpListener()
}