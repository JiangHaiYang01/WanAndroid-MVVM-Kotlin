package com.allens.model_base.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.allens.model_base.base.impl.BaseView
import com.allens.model_base.event.*
import com.allens.model_base.tools.BlueAction
import com.allens.model_base.tools.OnTouchHelperListener
import com.allens.model_base.tools.TouchHelper
import com.allens.status.NetworkHelper
import qiu.niorgai.StatusBarCompat
import java.util.concurrent.TimeUnit


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 13:42
 * @Version:        1.0
 */
abstract class BaseActivity : AppCompatActivity(), BaseView, OnTouchHelperListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentId = getContentViewId()
        if (contentId != 0) {
            setContentView(contentId)
        }
        setStatusBar()

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


        //用户去做什么
        initListener()
    }

    //==============================================================================================
    // 电池栏
    //==============================================================================================
    private fun setStatusBar() {
        //透明的电池栏
        //SDK >= 21时, 取消状态栏的阴影
        StatusBarCompat.translucentStatusBar(this, true)
        // SDK> = 23，将状态栏改为浅色模式（状态栏图标和字体会变成深色）
        StatusBarCompat.changeToLightStatusBar(this)
    }


    //==============================================================================================
    // 对外的抽象方法
    //==============================================================================================
    abstract fun getContentViewId(): Int


    protected abstract fun initListener()


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return when (ev!!.pointerCount) {
            1 -> super.dispatchTouchEvent(ev)
            2 -> TouchHelper.onTouchEvent(ev, this)
            else -> super.dispatchTouchEvent(ev)
        }
    }

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

    override fun onPinchZoom() {
        print("手势识别 两指 放大")
    }

    override fun onPinchGesture() {
        print("手势识别 捏合（两个手指，缩放手势）")
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
        startActivity(Intent(this, clz))
    }

    override fun startActivity(clz: Class<*>, bundle: Bundle) {
        val intent = Intent(this, clz)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun startActivityForResult(cls: Class<*>, bundle: Bundle, requestCode: Int) {
        val intent = Intent()
        intent.putExtras(bundle)
        startActivityForResult(intent, requestCode)
    }

    override fun hideSoftInput() {
        val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view: View? = currentFocus
        if(view!= null){
            service.hideSoftInputFromWindow(view.windowToken,InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


}