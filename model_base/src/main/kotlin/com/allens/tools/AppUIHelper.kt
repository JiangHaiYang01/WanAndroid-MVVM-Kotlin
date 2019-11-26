package com.allens.model_base.tools

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import com.allens.model_base.base.BaseApplication

/**
 * 应用前后台状态监听帮助类，仅在Application中使用
 * Created by dway on 2018/1/30.
 */

object AppUIHelper : ActivityLifecycleCallbacks {
    //打开的Activity数量统计
    private var activityStartCount = 0

    override fun onActivityStarted(activity: Activity) {
        activityStartCount++
        //数值从0变到1说明是从后台切到前台
        if (activityStartCount == 1) {
            //从后台切到前台
            listener.onAppUIStatus(false)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
        activityStartCount--
        //数值从1到0说明是从前台切到后台
        if (activityStartCount == 0) {
            //从前台切到后台
            listener.onAppUIStatus(true)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    private lateinit var listener: OnAppStatusListener

    fun register(listener: OnAppStatusListener) {
        this.listener = listener
        BaseApplication.instance.registerActivityLifecycleCallbacks(this)
    }

    fun unRegister() {
        BaseApplication.instance.unregisterActivityLifecycleCallbacks(this)
    }

}


interface OnAppStatusListener {
    fun onAppUIStatus(isBack :Boolean)
}