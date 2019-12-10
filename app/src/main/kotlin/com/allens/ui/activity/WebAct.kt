package com.allens.ui.activity

import android.content.DialogInterface
import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.allens.LogHelper
import com.allens.lib_ios_dialog.IosSheetDialog
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityWebBinding
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
            SheetDialog(this)
                .builder()
                .setTitle("测试")
                .addSheetItem("点击更多")
                .addSheetItem("点击更多点击更多点击更多")
                .show()
//            IosSheetDialog(this)
//                .builder()
//                .setTitle("测试")
//                .addSheetItem("1",null)
//                .addSheetItem("1",null)
//                .show()

        }
    }

}


class WebModel : BaseModel()

class WebVM : BaseVM<WebModel>()