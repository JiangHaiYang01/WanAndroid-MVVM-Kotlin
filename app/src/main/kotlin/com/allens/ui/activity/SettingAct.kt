package com.allens.ui.activity

import com.allens.bean.logout.LogOutBean
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.status.UserStatus
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivitySettinngBinding
import com.google.android.material.snackbar.Snackbar

class SettingAct : BaseMVVMAct<ActivitySettinngBinding, SettingModel, SettingVm>() {
    override fun initMVVMListener() {
        bind.actSettingLogOut.setOnClickListener {
            vm.logOut(object : OnBaseHttpListener<LogOutBean> {
                override fun onSuccess(t: LogOutBean) {
                    if (t.errorCode != 0) {
                        Snackbar.make(bind.parent, t.errorMsg, Snackbar.LENGTH_SHORT)
                            .show()
                        return
                    }
                    UserStatus.logOut(t)

                    //退出当前界面
                    finish()
                }

                override fun onError(e: Throwable) {
                    Snackbar.make(bind.parent, "退出失败,请检查网络后重试~", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_settinng
    }

    override fun createModel(): SettingModel {
        return SettingModel()
    }

    override fun createVMClass(): Class<SettingVm> {
        return SettingVm::class.java
    }

}


class SettingModel : BaseModel(), SettingModelImpl {
    override fun logOut(listener: OnBaseHttpListener<LogOutBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                LogOutBean::class.java,
                "user/logout/json",
                object : OnHttpListener<LogOutBean>() {
                    override fun onSuccess(t: LogOutBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class SettingVm : BaseVM<SettingModel>(), SettingModelImpl {
    override fun logOut(listener: OnBaseHttpListener<LogOutBean>) {
        model.logOut(listener)
    }

}

interface SettingModelImpl {
    fun logOut(listener: OnBaseHttpListener<LogOutBean>)
}