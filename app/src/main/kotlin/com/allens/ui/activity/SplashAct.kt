package com.allens.ui.activity

import com.allens.model_base.base.BaseActivity

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-26 11:55
 * @Version:        1.0
 */
class SplashAct : BaseActivity() {
    override fun getContentViewId(): Int {
        return 0
    }

    override fun initListener() {
        startActivity(MainActivity::class.java)
    }

}