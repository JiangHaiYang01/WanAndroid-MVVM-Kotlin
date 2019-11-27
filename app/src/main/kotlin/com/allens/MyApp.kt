package com.allens

import com.allens.model_base.base.BaseApplication
import com.allens.model_base.tools.FileHelper

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-26 11:54
 * @Version:        1.0
 */
class MyApp : BaseApplication() {
    override fun onAppCreate() {
        LogHelper.init(this, FileHelper.getBasePath() + "/log", 10, 5, true)
    }

}