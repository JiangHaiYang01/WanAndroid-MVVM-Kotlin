package com.allens.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.allens.bean.baner.BanerBean
import com.allens.model_base.base.BaseFragment
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgFindBinding
import com.allens.tools.databinding.FgHomeBinding
import com.allens.ui.adapter.BannerAdapter

//发现界面
class FindFragment : BaseMVVMFragment<FgFindBinding, FindModel, FindVM>() {


    override fun getContentViewId(): Int {
        return R.layout.fg_find
    }

    override fun createModel(): FindModel {
        return FindModel()
    }

    override fun createVMClass(): Class<FindVM> {
        return FindVM::class.java
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        //轮播图
        banner()
    }

    private fun banner() {
        vm.getBaner(object : OnBaseHttpListener<BanerBean> {
            override fun onError(e: Throwable) {

            }

            override fun onSuccess(t: BanerBean) {
                if (t.errorCode != 0) {
                    return
                }
                bind.banner.setImageLoader(BannerAdapter())
                val listOf = mutableListOf<String>()
                t.data.forEach {
                    listOf.add(it.imagePath)
                }
                bind.banner.setImages(listOf)
                bind.banner.start()
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
}


class FindModel : BaseModel(), FindModelImpl {
    override fun getBaner(listener: OnBaseHttpListener<BanerBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                BanerBean::class.java,
                "banner/json",
                object : OnHttpListener<BanerBean>() {
                    override fun onSuccess(t: BanerBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }


}

class FindVM : BaseVM<FindModel>(), FindModelImpl {
    override fun getBaner(listener: OnBaseHttpListener<BanerBean>) {
        model.getBaner(listener)
    }

}


interface FindModelImpl {
    fun getBaner(listener: OnBaseHttpListener<BanerBean>)
}