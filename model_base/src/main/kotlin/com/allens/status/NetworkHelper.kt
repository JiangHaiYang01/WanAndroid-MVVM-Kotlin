package com.allens.status

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.allens.model_base.base.BaseApplication

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 13:48
 * @Version:        1.0
 */

interface OnNetWorkListener {
    fun onNetWorkStatus(isMobileConn: Boolean, isWifiConn: Boolean)
}


class NetworkChangeReceiver(private val listener: OnNetWorkListener) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null) {
            return
        }
        val service: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var isMobileConn: Boolean = false
        var isWifiConn: Boolean = false
        for (i in service.allNetworks) {
            val networkInfo: NetworkInfo = (service.getNetworkInfo(i) ?: return) ?: return
            when (networkInfo.type) {
                ConnectivityManager.TYPE_MOBILE -> {
                    isMobileConn = networkInfo.isConnected
                }
                ConnectivityManager.TYPE_WIFI -> {
                    isWifiConn = networkInfo.isConnected
                }
            }
        }
        listener.onNetWorkStatus(isMobileConn, isWifiConn)
    }
}


object NetworkHelper {

    private lateinit var receiver: NetworkChangeReceiver

    fun register(context: Context, listener: OnNetWorkListener) {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        receiver =
            NetworkChangeReceiver(listener)
        context.registerReceiver(receiver, intentFilter)
    }

    fun unRegister(context: Context) {
        context.unregisterReceiver(receiver)
    }

    fun isNetworkAvailable(): Boolean {
        val manger: ConnectivityManager =
            BaseApplication.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manger.activeNetworkInfo
        return info != null && info.isAvailable
    }
}