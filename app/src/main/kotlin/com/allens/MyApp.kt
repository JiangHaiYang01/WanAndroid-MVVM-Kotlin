package com.allens

import com.allens.config.Config
import com.allens.model_base.base.BaseApplication
import com.allens.model_base.tools.FileHelper
import com.allens.model_http.XHttp
import com.tencent.mmkv.MMKV

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-26 11:54
 * @Version:        1.0
 */
class MyApp : BaseApplication() {


    override fun onAppCreate() {
        //日志
        init_Log()
        //key value 配置
        init_MMKV()
    }

    private fun init_MMKV() {
        MMKV.initialize(this)
    }

    private fun init_Log() {
        LogHelper.init(
            this,
            FileHelper.getBasePath() + Config.logPath,
            Config.maxLogRom,
            Config.maxLogFile,
            Config.isDebug
        )
    }

}