package com.allens.model_base.event

import androidx.lifecycle.MutableLiveData
import com.allens.model_base.tools.BlueAction
import java.util.concurrent.ConcurrentHashMap

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 16:40
 * @Version:        1.0
 */
object AppEventUtil {

    const val STATUS_NETWORK = "STATUS_NETWORK"
    const val STATUS_GPS = "STATUS_GPS"
    const val STATUS_BLUE = "STATUS_BLUE"
    const val STATUS_APP_FRONT_BACk = "STATUS_APP_FRONT_BACk"

    private val map: HashMap<String, MutableLiveData<Any>> = HashMap()

    fun <T> register(tag: String): MutableLiveData<T> {
        if (!map.contains(tag)) {
            map[tag] = MutableLiveData()
        }
        return map[tag] as MutableLiveData<T>
    }

    fun post(tag: String): MutableLiveData<Any> {
        return register(tag)
    }
}


//网络变化
data class NetWorkEvent(val isMobileConn: Boolean, val isWifiConn: Boolean)

//gps
data class GPSEvent(val isOpen: Boolean)

//blue
data class BlueEvent(val status: BlueAction)

//前后台状态
data class AppUIEvent(val isBack: Boolean)