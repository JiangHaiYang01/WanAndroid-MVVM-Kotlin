package com.allens.model_base.tools

import android.app.Activity
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.math.sqrt

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-12 17:41
 * @Version:        1.0
 */
object TouchHelper {

    private var nLenStart = 0.0
    private var nLenEnd = 0.0

    fun onTouchEvent(
        event: MotionEvent?,
        listener: OnTouchHelperListener
    ): Boolean {
        val pCount = event!!.pointerCount// 触摸设备时手指的数量
        val action = event.action// 获取触屏动作。比如：按下、移动和抬起等手势动作
        // 手势按下且屏幕上是两个手指数量时
        if (action and MotionEvent.ACTION_MASK == MotionEvent.ACTION_POINTER_DOWN && pCount == 2) {
            // 获取按下时候两个坐标的x轴的水平距离，取绝对值
            val xLen = abs(event.getX(0).toInt() - event.getX(1).toInt())
            // 获取按下时候两个坐标的y轴的水平距离，取绝对值
            val yLen = abs(event.getY(0).toInt() - event.getY(1).toInt())
            // 根据x轴和y轴的水平距离，求平方和后再开方获取两个点之间的直线距离。此时就获取到了两个手指刚按下时的直线距离
            nLenStart = sqrt(xLen.toDouble() * xLen + yLen.toDouble() * yLen)
        } else if (action and MotionEvent.ACTION_MASK == MotionEvent.ACTION_POINTER_UP && pCount == 2) {// 手势抬起且屏幕上是两个手指数量时
            // 获取抬起时候两个坐标的x轴的水平距离，取绝对值
            val xLen = abs(event.getX(0).toInt() - event.getX(1).toInt())
            // 获取抬起时候两个坐标的y轴的水平距离，取绝对值
            val yLen = abs(event.getY(0).toInt() - event.getY(1).toInt())
            // 根据x轴和y轴的水平距离，求平方和后再开方获取两个点之间的直线距离。此时就获取到了两个手指抬起时的直线距离
            nLenEnd = sqrt(xLen.toDouble() * xLen + yLen.toDouble() * yLen)
            // 根据手势按下时两个手指触点之间的直线距离A和手势抬起时两个手指触点之间的直线距离B。比较A和B的大小，得出用户是手势放大还是手势缩小
            if (nLenEnd > nLenStart) {
                listener.onPinchZoom()
            } else if (nLenEnd < nLenStart) {
                listener.onPinchGesture()
            }
        }
        return true
    }
}


interface OnTouchHelperListener {
    fun onPinchZoom()

    fun onPinchGesture()
}