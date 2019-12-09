package com.allens.ui.activity

import com.allens.LogHelper
import com.allens.bean.user_detail.UserDetailBean
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.status.UserStatus
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityMeBinding
import com.google.android.material.snackbar.Snackbar

class MeAct : BaseMVVMAct<ActivityMeBinding, MeActModel, MeActVM>() {
    override fun initMVVMListener() {

        //返回
        bind.actMeImgBack.setOnClickListener { finish() }

        //积分排名
        bind.actMeInfoView2.setInfo("总积分")
        bind.actMeInfoView3.setInfo("当前排名")
        bind.actMeInfoView1.setInfo("等级")


        //获取用户积分排行
        vm.getUserInfoDetail(object : OnBaseHttpListener<UserDetailBean> {
            override fun onSuccess(t: UserDetailBean) {
                if (t.errorCode != 0) {
                    return
                }
                UserStatus.setUserRank(t)
            }

            override fun onError(e: Throwable) {

            }
        })

    }

    override fun initMVVMBind() {
        bind.vm = vm
        bind.user = UserStatus
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_me
    }


    override fun createModel(): MeActModel {
        return MeActModel()
    }

    override fun createVMClass(): Class<MeActVM> {
        return MeActVM::class.java
    }

}

class MeActModel : BaseModel(), MeActModelImpl {
    //获取积分排行
    override fun getUserInfoDetail(listener: OnBaseHttpListener<UserDetailBean>) {

        HttpTool.xHttp
            .doGet(
                lifecycle,
                UserDetailBean::class.java,
                "lg/coin/userinfo/json",
                object : OnHttpListener<UserDetailBean>() {

                    override fun onSuccess(t: UserDetailBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }

}


class MeActVM : BaseVM<MeActModel>(), MeActModelImpl {

    //横图URL
    val defUrl =
        "https://c-ssl.duitang.com/uploads/item/201811/13/20181113104440_5w4kR.thumb.700_0.jpeg"

    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"


    override fun getUserInfoDetail(listener: OnBaseHttpListener<UserDetailBean>) {
        model.getUserInfoDetail(listener)
    }


}

interface MeActModelImpl {
    fun getUserInfoDetail(listener: OnBaseHttpListener<UserDetailBean>)
}