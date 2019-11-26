package com.allens.model_base.tools

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-13 11:59
 * @Version:        1.0
 */
object BlueHelper {

    private lateinit var blueBroadcast: BlueBroadcast

    fun register(context: Context, blueStatusListener: OnBlueStatusListener) {
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED")
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        intentFilter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_TURNING_OFF")
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_OFF")
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_TURNING_ON")
        intentFilter.addAction("android.bluetooth.BluetoothAdapter.STATE_ON")
        blueBroadcast = BlueBroadcast(blueStatusListener)
        context.registerReceiver(blueBroadcast, intentFilter)
    }


    fun unRegister(context: Context) {
        context.unregisterReceiver(blueBroadcast)
    }


}


class BlueBroadcast(blueStatusListener: OnBlueStatusListener) :
    BroadcastReceiver() {
    private val listener: OnBlueStatusListener = blueStatusListener
    override fun onReceive(
        context: Context?,
        intent: Intent
    ) {
        val action = intent.action ?: return
        //获取蓝牙设备实例【如果无设备链接会返回null，如果在无实例的状态下调用了实例的方法，会报空指针异常】
        //主要与蓝牙设备有关系


        val device =
            intent.getParcelableExtra<BluetoothDevice?>(BluetoothDevice.EXTRA_DEVICE)
        when (action) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> {
                listener.onBlueStatusChange(BlueAction.CONNECTED)
                if (device == null) {
                    return
                }
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                listener.onBlueStatusChange(BlueAction.DISCONNECTED)
                if (device == null) {
                    return
                }
            }
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                val blueState =
                    intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                when (blueState) {
                    BluetoothAdapter.STATE_OFF -> {
                        listener.onBlueStatusChange(BlueAction.STATE_OFF)
                    }
                    BluetoothAdapter.STATE_ON -> {
                        listener.onBlueStatusChange(BlueAction.STATE_ON)
                    }
                    BluetoothAdapter.STATE_TURNING_OFF -> {
                        listener.onBlueStatusChange(BlueAction.STATE_TURNING_OFF)
                    }
                    else -> {
                    }
                }
            }
            else -> {
            }
        }
    }

}


interface OnBlueStatusListener {
    fun onBlueStatusChange(status: BlueAction)
}

enum class BlueAction {
    CONNECTED, //连接成功
    DISCONNECTED, //连接丢失
    STATE_OFF, //蓝牙关闭
    STATE_ON, //蓝牙开启
    STATE_TURNING_OFF //蓝牙准备关闭
}
