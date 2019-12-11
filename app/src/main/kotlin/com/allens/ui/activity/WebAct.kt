package com.allens.ui.activity

import com.allens.LogHelper
import com.allens.bean.collection.CollectionBean
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityWebBinding
import com.allens.ui.dialog.ShareDialog
import com.google.android.material.snackbar.Snackbar
import dialog.SheetDialog


class WebAct : BaseMVVMAct<ActivityWebBinding, WebModel, WebVM>(),
    ShareDialog.OnShareDialogListener {

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
            val shareDialog = ShareDialog(this, this)
            shareDialog
                .create()
                .show()

        }
    }

    override fun onCollection() {
        val id = intent.getIntExtra(WEB_ID, -1)
        LogHelper.i("收藏 id $id  url ${intent.getStringExtra(WEB_URL)}")
        if (id == -1) {
            return
        }
        vm.collection(id, object : OnBaseHttpListener<CollectionBean> {
            override fun onError(e: Throwable) {
                Snackbar.make(
                    findViewById(R.id.act_web_ll_parent),
                    "请检查网络后重试~",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            override fun onSuccess(t: CollectionBean) {
                if (t.errorCode != 0) {
                    Snackbar.make(
                        findViewById(R.id.act_web_ll_parent),
                        t.errorMsg,
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return
                }

                Snackbar.make(
                    findViewById(R.id.act_web_ll_parent),
                    "收藏成功",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

}


class WebModel : BaseModel(), WebModelImpl {
    override fun collection(id: Int, listener: OnBaseHttpListener<CollectionBean>) {
        HttpTool.xHttp
            .doPost(
                CollectionBean::class.java,
                "lg/collect/$id/json",
                object : OnHttpListener<CollectionBean>() {
                    override fun onError(e: Throwable) {
                    }

                    override fun onSuccess(t: CollectionBean) {
                    }
                })
    }

}

class WebVM : BaseVM<WebModel>(), WebModelImpl {
    override fun collection(id: Int, listener: OnBaseHttpListener<CollectionBean>) {
        model.collection(id, listener)
    }

}


interface WebModelImpl {
    fun collection(id: Int, listener: OnBaseHttpListener<CollectionBean>)
}