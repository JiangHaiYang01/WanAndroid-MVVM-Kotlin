package com.allens.model_base.tools

import android.Manifest.permission.READ_PHONE_NUMBERS
import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import java.util.*
import java.util.jar.Manifest


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-23 14:24
 * @Version:        1.0
 */
//获取厂商名
fun getDeviceManufacturer(): String {
    return Build.MANUFACTURER
}

//获取产品名
fun getDeviceProduct(): String {
    return Build.PRODUCT
}


//手机品牌
fun getBrand(): String {
    return Build.BRAND
}

//手机型号
fun getModel(): String {
    return Build.MODEL
}

//获取手机主板名
fun getDeviceBoard(): String {
    return Build.BOARD
}

//设备名
fun getDeviceDevice(): String {
    return Build.DEVICE
}

//fingerprit 信息
fun getDeviceFubgerprint(): String {
    return Build.FINGERPRINT
}

//android 版本
fun getAndroidVersion(): String {
    return Build.VERSION.RELEASE
}

//android sdk 版本
fun getAndroidSDKVersion(): Int {
    return Build.VERSION.SDK_INT
}

//获取设备宽度（px）
fun getDeviceWidth(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

//获取设备高度（px）
fun getDeviceHeight(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}


//获取当前手机系统语言。
fun getDeviceDefaultLanguage(): Locale {
    return Locale.getDefault()
}

//获取当前系统上的语言列表(Locale列表)
fun getDeviceSupportLanguage(): Array<Locale> {
    return Locale.getAvailableLocales()
}


//获取手机号码
@SuppressLint("MissingPermission", "HardwareIds")
fun getUserPhone(context: Context): String? {
    if (ContextCompat.checkSelfPermission(
            context,
            READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val systemService =
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return systemService.line1Number
    }
    return null
}