package com.allens.model_base.tools

import android.os.Environment
import com.allens.model_base.base.BaseApplication
import java.io.File

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-13 11:03
 * @Version:        1.0
 */
object FileHelper {

    fun getBasePath(): String {
        var p: String = Environment.getExternalStorageDirectory().path
        val f: File? = BaseApplication.instance.getExternalFilesDir(null)
        if (null != f) {
            p = f.absolutePath
        }
        return p
    }
}
