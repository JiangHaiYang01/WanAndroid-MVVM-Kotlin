package com.allens

import android.content.Context
import android.content.SharedPreferences

/**
 *
 * log  位置的记录
 * Created by allens on 2017/11/30.
 */
class ShareLogUtil(context: Context) {
    private var path = "allens_sp_log"
    private val sp: SharedPreferences?
    fun setPath(path: String) {
        this.path = path
    }

    fun putBoolean(key: String?, value: Boolean) {
        sp!!.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return sp!!.getBoolean(key, defValue)
    }

    fun putString(key: String?, value: String?) {
        sp!!.edit().putString(key, value).apply()
    }

    fun getString(key: String?, defValue: String?): String? {
        return sp!!.getString(key, defValue)
    }

    fun putInt(key: String?, value: Int) {
        sp!!.edit().putInt(key, value).apply()
    }

    fun getInt(key: String?, defValue: Int): Int {
        return sp!!.getInt(key, defValue)
    }

    fun putLong(key: String?, value: Long?) {
        sp!!.edit().putLong(key, value!!).apply()
    }

    fun getLong(key: String?, defValue: Long?): Long {
        return sp!!.getLong(key, defValue!!)
    }

    fun remove(key: String?) {
        sp!!.edit().remove(key).apply()
    }

    fun clear() {
        sp?.edit()?.clear()?.apply()
    }

    companion object {
        private var prefUtils: ShareLogUtil? = null
        fun create(context: Context): ShareLogUtil? {
            if (prefUtils == null) {
                synchronized(ShareLogUtil::class.java) {
                    if (prefUtils == null) {
                        prefUtils = ShareLogUtil(context)
                    }
                }
            }
            return prefUtils
        }
    }

    init {
        sp = context.getSharedPreferences(path, Context.MODE_PRIVATE)
    }
}