package com.allens.ui.activity

import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityWebBinding
import com.allens.ui.dialog.ShareDialog
import dialog.SheetDialog


class WebAct : BaseMVVMAct<ActivityWebBinding, WebModel, WebVM>() {

    companion object {
        const val WEB_URL = "URL"
        const val WEB_ID = "ID"
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
        bind.actWeb.loadUrl(url)



        bind.actWebImgBack.setOnClickListener {
            finish()
        }

        bind.actWebImgMore.setOnClickListener {
            LogHelper.i("web act 点击更多")
            ShareDialog(this)
                .create()
                .show()

        }
    }

}


class WebModel : BaseModel()

class WebVM : BaseVM<WebModel>()