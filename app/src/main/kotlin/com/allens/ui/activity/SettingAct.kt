package com.allens.ui.activity

import com.alibaba.android.arouter.launcher.ARouter
import com.allens.bean.logout.LogOutBean
import com.allens.data.dto.FgItemDto
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.status.UserStatus
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivitySettinngBinding
import com.allens.ui.adapter.MeFragmentItemAdapter
import com.google.android.material.snackbar.Snackbar

class SettingAct : BaseMVVMAct<ActivitySettinngBinding, SettingModel, SettingVm>(),
    MeFragmentItemAdapter.OnMeFragmentItemClickListener {
    override fun initMVVMListener() {


        //退出登录
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

        //开发测试
        bind.fgMeRy.adapter = MeFragmentItemAdapter(vm.model.getItemData(), this)

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

    override fun onFragmentItemClick(pos: Int, item: FgItemDto) {
        when (item.tag) {
            //aws 测试
            1 -> {
                ARouter.getInstance().build("/aws/act").navigation()
            }
        }
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

    fun getItemData(): MutableList<FgItemDto> {
        return mutableListOf(
            FgItemDto(R.drawable.fg_me_options, "AWS测试", false, 1)
        )
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