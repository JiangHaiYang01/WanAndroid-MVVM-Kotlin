package com.allens.ui.fragment.hot_detail

import androidx.lifecycle.LifecycleOwner
import com.allens.LogHelper
import com.allens.bean.HomeDetailBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHotOfficialBinding
import kotlinx.android.synthetic.main.fg_home_vp.view.*

//公众号
class OfficialFg : BaseMVVMFragment<FgHotOfficialBinding, OfficialModel, OfficialVM>() {
    override fun createModel(): OfficialModel {
        return OfficialModel()
    }

    override fun createVMClass(): Class<OfficialVM> {
        return OfficialVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_hot_official
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
    }
}


class OfficialModel : BaseModel() {
    fun getOfficialTab(
        listener: OnBaseHttpListener<HomeDetailBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                HomeDetailBean::class.java,
                "wxarticle/chapters/json",
                object : OnHttpListener<HomeDetailBean>() {
                    override fun onSuccess(t: HomeDetailBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class OfficialVM : BaseVM<OfficialModel>()