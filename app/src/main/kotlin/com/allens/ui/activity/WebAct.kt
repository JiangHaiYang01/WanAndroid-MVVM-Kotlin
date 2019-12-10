package com.allens.ui.activity

import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityWebBinding
import kotlinx.android.synthetic.main.activity_me.view.*

class WebAct : BaseMVVMAct<ActivityWebBinding, WebModel, WebVM>() {

    companion object {
        const val WEB_URL = "URL"
    }


    override fun createModel(): WebModel {
        return WebModel()
    }

    override fun createVMClass(): Class<WebVM> {
        return WebVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_web
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        val url = intent.getStringExtra(WEB_URL)
        LogHelper.i("web view load $url")
        bind.actWeb.loadUrl(url)



        bind.actWebImgBack.setOnClickListener {
            finish()
        }
    }

}


class WebModel : BaseModel()

class WebVM : BaseVM<WebModel>()