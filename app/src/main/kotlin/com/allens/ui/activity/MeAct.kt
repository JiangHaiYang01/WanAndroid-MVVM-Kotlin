package com.allens.ui.activity

import com.allens.LogHelper
import com.allens.bean.home_detail.DataX
import com.allens.bean.user_detail.UserDetailBean
import com.allens.model_base.base.BaseFragment
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.status.UserStatus
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityMeBinding
import com.allens.ui.adapter.FindDetailAdapter
import com.allens.ui.adapter.HotFgPagerAdapter
import com.allens.ui.adapter.MeDetailAdapter
import com.allens.ui.fragment.hot.hot_detail.HotTabFragment
import com.allens.ui.fragment.hot.hot_detail.NewProjectFg
import com.allens.ui.fragment.hot.hot_detail.OfficialFg
import com.allens.ui.fragment.hot.hot_detail.ProjectFg
import com.allens.ui.fragment.me.CollectionFragment
import com.google.android.material.snackbar.Snackbar

class MeAct : BaseMVVMAct<ActivityMeBinding, MeActModel, MeActVM>() {
    override fun initMVVMListener() {

        //返回
        bind.actMeImgBack.setOnClickListener { finish() }

        //积分排名
        meInfo()

        addTab()
        addViewPager()

    }

    private fun meInfo() {

        bind.actMeInfoView2.setInfo("总积分")
        bind.actMeInfoView3.setInfo("当前排名")
        bind.actMeInfoView1.setInfo("等级")
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


    private fun addViewPager() {
        bind.actMeVp.adapter = HotFgPagerAdapter(vm.fragList, vm.tabList, supportFragmentManager)
        bind.fgHotTlParent.setupWithViewPager(bind.actMeVp)
    }

    private fun addTab() {
        vm.tabList.forEach {
            bind.fgHotTlParent.addTab(bind.fgHotTlParent.newTab().setText(it))
        }
    }

}

class MeActModel : BaseModel(), MeActModelImpl {


}


class MeActVM : BaseVM<MeActModel>(), MeActModelImpl {


    var tabList = mutableListOf(
        "收藏文章",
        "收藏网站",
        "我的分享"
    )
    var fragList = mutableListOf<BaseFragment>(
        CollectionFragment(),
        OfficialFg(),
        ProjectFg()
    )

    //横图URL
    val defUrl =
        "https://c-ssl.duitang.com/uploads/item/201811/13/20181113104440_5w4kR.thumb.700_0.jpeg"

    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"


}

interface MeActModelImpl {
}