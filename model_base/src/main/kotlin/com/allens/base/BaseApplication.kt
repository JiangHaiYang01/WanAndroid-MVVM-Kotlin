package com.allens.model_base.base

import android.app.Application
import com.allens.model_base.event.*
import com.allens.model_base.tools.*
import com.allens.status.GpsHelper
import com.allens.status.NetworkHelper
import com.allens.status.OnGPSStatusChangeListener
import com.allens.status.OnNetWorkListener


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 11:25
 * @Version:        1.0
 */

abstract class BaseApplication : Application(),
    OnGPSStatusChangeListener,
    OnNetWorkListener,
    OnBlueStatusListener, OnAppStatusListener {


    companion object {
        lateinit var instance: Application
    }


    override fun onCreate() {
        super.onCreate()
        instance = this

        //网络
        NetworkHelper.register(this, this)
        //gps
        GpsHelper.register(this, this)
        //blue
        BlueHelper.register(this, this)
        //app 前后台状态
        AppUIHelper.register(this)
        //对外
        onAppCreate()

    }

    override fun onTerminate() {
        super.onTerminate()
        //网络
        NetworkHelper.unRegister(this)
        //gps
        GpsHelper.unRegister(this)
        //blue
        BlueHelper.unRegister(this)
        //app 前后台状态
        AppUIHelper.unRegister()
    }


    override fun onNetWorkStatus(isMobileConn: Boolean, isWifiConn: Boolean) {
        println("网络变化 手机网络 $isMobileConn  wifi网络 $isWifiConn")
        AppEventUtil.post(AppEventUtil.STATUS_NETWORK).value =
            NetWorkEvent(isMobileConn, isWifiConn)
    }

    override fun onGPSChange(isOpen: Boolean) {
        println("gps是否开启 $isOpen")
        AppEventUtil.post(AppEventUtil.STATUS_GPS).value = GPSEvent(isOpen)
    }

    override fun onBlueStatusChange(status: BlueAction) {
        println("blue 状态 $status")
        AppEventUtil.post(AppEventUtil.STATUS_BLUE).value = BlueEvent(status)
    }

    override fun onAppUIStatus(isBack: Boolean) {
        println("app 当前是否在后台 $isBack")
        AppEventUtil.post(AppEventUtil.STATUS_APP_FRONT_BACk).value = AppUIEvent(isBack)
    }


    //==============================================================================================
    // 对外接口
    //==============================================================================================

    protected abstract fun onAppCreate()


}