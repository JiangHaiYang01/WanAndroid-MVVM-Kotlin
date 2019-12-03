package com.allens.ui.fragment

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.allens.bean.HomeDetailBean
import com.allens.bean.SystemResultBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHomeVpBinding
import com.allens.ui.adapter.HomeDetailAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener

class HomeVpFg(private val data: SystemResultBean) :
    BaseMVVMFragment<FgHomeVpBinding, HomeVPModel, HomeVPVM>() {
    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {

        //添加子集
        for (index in data.children.indices) {
            val tab = bind.fgHomeVpTlParent.newTab()
            tab.setCustomView(R.layout.view_custom_tab)
            val textView = tab.customView?.findViewById<TextView>(R.id.view_custom_tab_tv)
            textView?.text = data.children[index].name
            setTabSelect(tab)
            bind.fgHomeVpTlParent.addTab(tab)
        }


        //默认加载第一个
        getDetailData(pos = 0)


        //选中事件监听
        bind.fgHomeVpTlParent.addOnTabSelectedListener(object :
            BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(ta1: TabLayout.Tab?) {
            }

            override fun onTabUnselected(ta1: TabLayout.Tab?) {
                setTabSelect(ta1)
            }

            override fun onTabSelected(ta1: TabLayout.Tab?) {
                setTabSelect(ta1)

                getDetailData(pos = ta1?.position)
            }
        })


    }

    //请求数据源
    fun getDetailData(pos: Int?) {
        if (pos == null) {
            return
        }
        vm.getDetail(0, data.children[pos].id, object : OnBaseHttpListener<HomeDetailBean> {
            override fun onSuccess(t: HomeDetailBean) {
                if (t.errorCode != 0) {
                    return
                }

                bind.fgHomeTlRv.adapter = HomeDetailAdapter(t.data.datas)

            }

            override fun onError(e: Throwable) {
            }
        })
    }


    fun setTabSelect(tab: TabLayout.Tab?) {
        if (tab == null) {
            return
        }
        val textView = tab.customView?.findViewById<TextView>(R.id.view_custom_tab_tv)
        if (tab.isSelected) {
            textView?.setBackgroundResource(R.drawable.bg_tab_blue)
        } else {
            textView?.setBackgroundResource(R.drawable.bg_tab_gray)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_home_vp
    }

    override fun createModel(): HomeVPModel {
        return HomeVPModel()
    }

    override fun createVMClass(): Class<HomeVPVM> {
        return HomeVPVM::class.java
    }

}


class HomeVPModel : BaseModel {
    fun getDetail(
        lifecycleOwner: LifecycleOwner,
        curPage: Int,
        cid: Int,
        listener: OnBaseHttpListener<HomeDetailBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycleOwner,
                HomeDetailBean::class.java,
                "article/list/$curPage/json?cid=$cid",
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


class HomeVPVM : BaseVM<HomeVPModel>() {
    fun getDetail(
        curPage: Int,
        cid: Int, listener: OnBaseHttpListener<HomeDetailBean>
    ) {
        model.getDetail(lifecycle, curPage, cid, listener)
    }
}