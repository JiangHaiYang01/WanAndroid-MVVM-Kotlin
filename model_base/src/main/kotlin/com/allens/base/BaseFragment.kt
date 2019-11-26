package com.allens.model_base.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.allens.model_base.base.impl.BaseView
import com.allens.model_base.event.*
import com.allens.model_base.tools.BlueAction
import com.allens.status.NetworkHelper
import java.util.concurrent.TimeUnit


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 13:42
 * @Version:        1.0
 */
abstract class BaseFragment : Fragment(), BaseView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //网络变化
        AppEventUtil.register<NetWorkEvent>(AppEventUtil.STATUS_NETWORK)
            .observe(this, Observer { onNewWorkChange(it.isMobileConn, it.isWifiConn) })

        //gps
        AppEventUtil.register<GPSEvent>(AppEventUtil.STATUS_GPS)
            .observe(this, Observer { onGpsChange(it.isOpen) })

        //blue
        AppEventUtil.register<BlueEvent>(AppEventUtil.STATUS_BLUE)
            .observe(this, Observer { onBlueChange(it.status) })

        //app 前后台变化
        AppEventUtil.register<AppUIEvent>(AppEventUtil.STATUS_APP_FRONT_BACk)
            .observe(this, Observer { onAppUIChange(it.isBack) })


    }

    private lateinit var inflate: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflate = inflater.inflate(getContentViewId(), container, false)
        bindView(inflater, container, savedInstanceState, inflate)
        return inflate
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    //==============================================================================================
    // 对外的抽象方法
    //==============================================================================================
    abstract fun getContentViewId(): Int


    protected abstract fun initListener()

    abstract fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
        inflate: View
    )


    //==============================================================================================
    // 状态变化
    //==============================================================================================
    override fun onNewWorkChange(isMobileConn: Boolean, isWifiConn: Boolean) {
    }

    override fun onBlueChange(status: BlueAction) {

    }

    override fun onGpsChange(isOpen: Boolean) {

    }

    override fun onAppUIChange(isBack: Boolean) {
    }


    //==============================================================================================
    // 方法
    //==============================================================================================
    override fun isNetworkAvailable(): Boolean {
        return NetworkHelper.isNetworkAvailable()
    }

    override fun getStringResId(id: Int): String {
        return resources.getString(id)
    }

    override fun clickThrottleFirst(view: View, listener: View.OnClickListener?) {
    }

    override fun clickThrottleFirst(
        view: View,
        windowDuration: Long,
        unit: TimeUnit,
        listener: View.OnClickListener
    ) {
    }

    override fun startActivity(clz: Class<*>) {
        startActivity(Intent(activity, clz))
    }

    override fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent()
        activity?.let { intent.setClass(it, clz) }
        startActivity(intent)
    }

    override fun startActivityForResult(cls: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent()
        activity?.let { intent.setClass(it, cls) }
        startActivityForResult(intent, requestCode)
    }


}