package com.allens

import com.allens.model_base.base.BaseApplication
import com.allens.model_base.tools.FileHelper
import com.allens.model_http.XHttp

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-26 11:54
 * @Version:        1.0
 */
class MyApp : BaseApplication() {

    lateinit var xHttp: XHttp

    override fun onAppCreate() {
        LogHelper.init(
            this,
            FileHelper.getBasePath() + Config.logPath,
            Config.maxLogRom,
            Config.maxLogFile,
            Config.isDebug
        )


    }

}