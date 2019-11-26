package com.allens.status

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-13 11:50
 * @Version:        1.0
 */
object GpsHelper {
    private lateinit var receiver: GpsStatusReceiver

    fun register(
        context: Context,
        listener: OnGPSStatusChangeListener
    ) {
        val filter = IntentFilter()
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        receiver =
            GpsStatusReceiver(listener)
        context.registerReceiver(receiver, filter)
    }

    fun unRegister(context: Context) {
        context.unregisterReceiver(receiver)
    }
}

class GpsStatusReceiver(private val listener: OnGPSStatusChangeListener) :
    BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        val action = intent.action
        if (action != null)
            if (action == LocationManager.PROVIDERS_CHANGED_ACTION) {
                listener.onGPSChange(getGPSState(context))
            }
    }

    /**
     * 获取ＧＰＳ当前状态
     *
     * @param context
     * @return
     */
    private fun getGPSState(context: Context): Boolean {
        val locationManager =
            (context.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}

interface OnGPSStatusChangeListener {
    fun onGPSChange(isOpen: Boolean)
}