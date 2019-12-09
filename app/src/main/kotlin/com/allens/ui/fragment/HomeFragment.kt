package com.allens.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.allens.bean.SystemBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHomeBinding
import com.allens.ui.adapter.HomeFgPagerAdapter
import java.util.*

class HomeFragment : BaseMVVMFragment<FgHomeBinding, HomeModel, HomeVM>() {
    override fun initMVVMListener() {


        vm.getSystemTab(object : OnBaseHttpListener<SystemBean> {
            override fun onSuccess(t: SystemBean) {
                if (t.errorCode != 0) {
                    return
                }
                //父类
                val list = ArrayList<Fragment>()
                for (info in t.data.indices) {
                    list.add(HomeVpFg(t.data[info]))
                }
                bind.fgHomeVp.adapter = HomeFgPagerAdapter(list, t, childFragmentManager)
                bind.fgHomeTlParent.setupWithViewPager(bind.fgHomeVp);
            }

            override fun onError(e: Throwable) {
            }
        })


    }

    override fun initMVVMBind() {
        bind.vm = vm
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_home
    }

    override fun createModel(): HomeModel {
        return HomeModel()
    }

    override fun createVMClass(): Class<HomeVM> {
        return HomeVM::class.java
    }

}


class HomeModel : BaseModel() {

    fun getSystemTab(lifecycle: LifecycleOwner, listener: OnBaseHttpListener<SystemBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                SystemBean::class.java,
                "tree/json",
                object : OnHttpListener<SystemBean>() {
                    override fun onSuccess(t: SystemBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class HomeVM : BaseVM<HomeModel>(), HomeModelImpl {
    override fun getSystemTab(listener: OnBaseHttpListener<SystemBean>) {
        model.getSystemTab(lifecycle, listener)
    }

}

interface HomeModelImpl {
    fun getSystemTab(listener: OnBaseHttpListener<SystemBean>)
}