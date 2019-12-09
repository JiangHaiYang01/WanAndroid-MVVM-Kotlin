package com.allens.ui.fragment.hot_detail

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.allens.LogHelper
import com.allens.bean.home_system_detail.DataX
import com.allens.bean.offlicial_tab.OfflicialTabInfoBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHotOfficialBinding
import com.allens.ui.adapter.HomeDetailAdapter
import com.google.android.material.tabs.TabLayout
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


        vm.getOfficialTab(object : OnBaseHttpListener<OfflicialTabInfoBean> {
            override fun onError(e: Throwable) {

            }

            override fun onSuccess(t: OfflicialTabInfoBean) {
                if (t.errorCode != 0) {
                    return
                }

                addOfficialTab(t)
                //自动刷新
                bind.fgHomeRefresh.autoRefresh()
                //添加选择器
                addOfficialTabSelect(t)
                //
                addRefresh(t);
            }
        })
    }

    private fun addRefresh(t: OfflicialTabInfoBean) {
        bind.fgHomeRefresh.setOnRefreshListener {
            LogHelper.i("下拉刷新 ----> ${t.data[vm.index].name}")
            refresh(vm.index, t)
        }

        bind.fgHomeRefresh.setOnLoadMoreListener {
            LogHelper.i("加载更多 ----> ${t.data[vm.index].name} pageIndex ${vm.pageIndex}")
//            loadMore(vm.index, vm.pageIndex)
        }
    }

    private fun refresh(index: Int, t: OfflicialTabInfoBean) {
        var id =  t.data[index].id
//        vm.getOfficialDetail(id,0,object :OnBaseHttpListener<>)
    }


    private fun addOfficialTabSelect(t: OfflicialTabInfoBean) {
        //选中事件监听
        bind.fgHomeVpTlParent.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(ta1: TabLayout.Tab?) {
            }

            override fun onTabUnselected(ta1: TabLayout.Tab?) {
                setTabSelect(ta1)
            }

            override fun onTabSelected(ta1: TabLayout.Tab?) {
                setTabSelect(ta1)
                if (ta1 != null) {
                    vm.pageIndex = 0
                    vm.data.clear()
                    vm.index = ta1.position
                    //自动刷新
                    bind.fgHomeRefresh.autoRefresh()
                }
            }
        })
    }

    private fun addOfficialTab(t: OfflicialTabInfoBean) {
        t.data.forEach {
            val tab = bind.fgHomeVpTlParent.newTab()
            tab.setCustomView(R.layout.view_custom_tab)
            val textView = tab.customView?.findViewById<TextView>(R.id.view_custom_tab_tv)
            textView?.text = it.name
            setTabSelect(tab)
            bind.fgHomeVpTlParent.addTab(tab)
        }
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
}


class OfficialModel : BaseModel(), OfficialModelImpl {
    override fun getOfficialTab(
        listener: OnBaseHttpListener<OfflicialTabInfoBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                OfflicialTabInfoBean::class.java,
                "wxarticle/chapters/json",
                object : OnHttpListener<OfflicialTabInfoBean>() {
                    override fun onSuccess(t: OfflicialTabInfoBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }

    override fun getOfficialDetail(
        id: Int,
        pageIndex: Int,
        listener: OnBaseHttpListener<OfflicialTabInfoBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                OfflicialTabInfoBean::class.java,
                "wxarticle/list/$id/$pageIndex/json",
                object : OnHttpListener<OfflicialTabInfoBean>() {
                    override fun onSuccess(t: OfflicialTabInfoBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class OfficialVM : BaseVM<OfficialModel>(), OfficialModelImpl {


    //当前显示第几个
    var index = 0
    //当前加载第几页
    var pageIndex = 0

    //数据源
    var data: MutableList<DataX> = mutableListOf()

    //数据源
    val adapter: HomeDetailAdapter = HomeDetailAdapter(data)


    override fun getOfficialTab(listener: OnBaseHttpListener<OfflicialTabInfoBean>) {
        model.getOfficialTab(listener)
    }

    override fun getOfficialDetail(
        id: Int,
        pageIndex: Int,
        listener: OnBaseHttpListener<OfflicialTabInfoBean>
    ) {
        model.getOfficialDetail(id,pageIndex,listener)
    }

}


interface OfficialModelImpl {
    fun getOfficialTab(
        listener: OnBaseHttpListener<OfflicialTabInfoBean>
    )

    fun getOfficialDetail(
        id: Int,
        pageIndex: Int,
        listener: OnBaseHttpListener<OfflicialTabInfoBean>
    )
}