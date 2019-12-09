package com.allens.ui.fragment.home

import androidx.fragment.app.Fragment
import com.allens.bean.home_system_tab.HomeSystemTabBean
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


        vm.getSystemTab(object : OnBaseHttpListener<HomeSystemTabBean> {
            override fun onSuccess(t: HomeSystemTabBean) {
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


class HomeModel : BaseModel(), HomeModelImpl {

    override fun getSystemTab(listener: OnBaseHttpListener<HomeSystemTabBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                HomeSystemTabBean::class.java,
                "tree/json",
                object : OnHttpListener<HomeSystemTabBean>() {
                    override fun onSuccess(t: HomeSystemTabBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class HomeVM : BaseVM<HomeModel>(),
    HomeModelImpl {
    override fun getSystemTab(listener: OnBaseHttpListener<HomeSystemTabBean>) {
        model.getSystemTab(listener)
    }

}

interface HomeModelImpl {
    fun getSystemTab(listener: OnBaseHttpListener<HomeSystemTabBean>)
}