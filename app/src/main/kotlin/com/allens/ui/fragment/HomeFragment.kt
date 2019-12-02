package com.allens.ui.fragment

import com.allens.LogHelper
import com.allens.bean.BannerBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHomeBinding

class HomeFragment : BaseMVVMFragment<FgHomeBinding, HomeModel, HomeVM>() {
    override fun initMVVMListener() {
        //加载轮播图
        vm.getBanner(object : OnBaseHttpListener<BannerBean> {
            override fun onSuccess(t: BannerBean) {
                LogHelper.i("baner 加载成功")
                if (t.errorCode != 0) {
                    return
                }
                //设置banner
                val list = ArrayList<String>()
                for (data in t.data) {
                    list.add(data.imagePath)
                }
                LogHelper.i("load img url $list")
                bind.fgHomeBanner.setImages(list);
//                bind.fgHomeBanner.setImageLoader(ImageBannerLoader())
                bind.fgHomeBanner.start()

            }

            override fun onError(e: Throwable) {
                LogHelper.i("baner 加载失败 ${e.message}")
            }
        })
    }


    override fun onStart() {
        super.onStart()
        bind.fgHomeBanner.startAutoPlay()
    }

    override fun onStop() {
        super.onStop()
        bind.fgHomeBanner.stopAutoPlay()
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


class HomeModel : BaseModel, HomeModelImpl {

    override fun getBanner(listener: OnBaseHttpListener<BannerBean>) {
        HttpTool.xHttp
            .doGet(BannerBean::class.java, "/banner/json", object : OnHttpListener<BannerBean>() {
                override fun onSuccess(t: BannerBean) {
                    listener.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    listener.onError(e)
                }
            })
    }

}


class HomeVM : BaseVM<HomeModel>(), HomeModelImpl {
    override fun getBanner(listener: OnBaseHttpListener<BannerBean>) {
        model.getBanner(listener)
    }
}

interface HomeModelImpl {
    fun getBanner(listener: OnBaseHttpListener<BannerBean>)
}