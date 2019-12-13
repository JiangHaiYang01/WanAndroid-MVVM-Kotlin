package com.allens.ui.fragment.hot

import android.content.Intent
import android.os.Bundle
import com.allens.LogHelper
import com.allens.bean.baner.BannerBean
import com.allens.model_base.base.BaseFragment
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHotBinding
import com.allens.ui.activity.WebAct
import com.allens.ui.adapter.HotFgPagerAdapter
import com.allens.ui.adapter.ImageBannerLoader
import com.allens.ui.fragment.hot.hot_detail.HotTabFragment
import com.allens.ui.fragment.hot.hot_detail.NewProjectFg
import com.allens.ui.fragment.hot.hot_detail.OfficialFg
import com.allens.ui.fragment.hot.hot_detail.ProjectFg

class HotFragment : BaseMVVMFragment<FgHotBinding, HotModel, HotFgVM>() {
    override fun createModel(): HotModel {
        return HotModel()
    }

    override fun createVMClass(): Class<HotFgVM> {
        return HotFgVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_hot
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        addTab()
        addViewPager()
        addBanner()
    }

    private fun addBanner() {
        vm.getBanner(object : OnBaseHttpListener<BannerBean> {
            override fun onError(e: Throwable) {

            }

            override fun onSuccess(t: BannerBean) {
                if (t.errorCode != 0) {
                    return
                }
                bind.banner.setImageLoader(ImageBannerLoader())

                val list = mutableListOf<String>()
                t.data.forEach {
                    list.add(it.imagePath)
                }
                bind.banner.setImages(list)
                bind.banner.start()

                bind.banner.setOnBannerListener {
                    LogHelper.i("banner click $it")

                    val bundle = Bundle()
                    bundle.putString(WebAct.WEB_URL, t.data[it].url)
//                    bundle.putInt(WebAct.WEB_ID, item.id)
                    val intent = Intent(activity, WebAct::class.java)
                    intent.putExtras(bundle)
                    startActivity(WebAct::class.java, bundle)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        bind.banner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        bind.banner.stopAutoPlay()
    }

    private fun addViewPager() {
        bind.fgHotVp.adapter = HotFgPagerAdapter(vm.fragList, vm.tabList, childFragmentManager)
        bind.fgHotTlParent.setupWithViewPager(bind.fgHotVp);
    }

    private fun addTab() {
        vm.tabList.forEach {
            bind.fgHotTlParent.addTab(bind.fgHotTlParent.newTab().setText(it))
        }
    }
}


class HotModel : BaseModel(), HotModelImpl {
    override fun getBanner(listener: OnBaseHttpListener<BannerBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                BannerBean::class.java,
                "banner/json",
                object : OnHttpListener<BannerBean>() {
                    override fun onSuccess(t: BannerBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }

}


class HotFgVM : BaseVM<HotModel>(), HotModelImpl {


    var tabList = mutableListOf(
        "推荐",
        "最新项目",
        "公众号",
        "开源推荐"
    )
    var fragList = mutableListOf<BaseFragment>(
        HotTabFragment(),
        NewProjectFg(),
        OfficialFg(),
        ProjectFg()
    )

    override fun getBanner(listener: OnBaseHttpListener<BannerBean>) {
        model.getBanner(listener)
    }
}


interface HotModelImpl {
    fun getBanner(listener: OnBaseHttpListener<BannerBean>)
}