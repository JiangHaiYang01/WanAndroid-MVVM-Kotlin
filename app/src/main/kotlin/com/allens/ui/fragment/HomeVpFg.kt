package com.allens.ui.fragment

import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import com.allens.LogHelper
import com.allens.bean.HomeDetailBean
import com.allens.bean.HomeDetailResultDataBean
import com.allens.bean.SystemResultBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHomeVpBinding
import com.allens.ui.activity.AuthorAct
import com.allens.ui.adapter.HomeDetailAdapter
import com.allens.ui.adapter.OnHomeDetailAdapterListener
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

        //自动刷新
        bind.fgHomeRefresh.autoRefresh()


        vm.adapter.setOnHomeDetailAdapterListener(object : OnHomeDetailAdapterListener {
            override fun onClickHomeDetailAuthor(item: HomeDetailResultDataBean) {
                LogHelper.i("home fg 点击 作者 ${item.author}")
                if (item.author.isEmpty()) {
                    return
                }
                val bundle = Bundle()
                bundle.putString("author", item.author)
                startActivity(AuthorAct::class.java, bundle)
            }

            override fun onClickHomeDetailItem(item: HomeDetailResultDataBean) {
                LogHelper.i("home fg 点击 item ${item.author}")
            }
        })


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
                if (ta1 != null) {
                    vm.pageIndex = 0
                    vm.data.clear()
                    vm.index = ta1.position
                    //自动刷新
                    bind.fgHomeRefresh.autoRefresh()
                }
            }
        })


        bind.fgHomeRefresh.setOnRefreshListener {
            LogHelper.i("下拉刷新 ----> ${data.children[vm.index].name}")
            refresh(vm.index)
        }

        bind.fgHomeRefresh.setOnLoadMoreListener {
            LogHelper.i("加载更多 ----> ${data.children[vm.index].name} pageIndex ${vm.pageIndex}")
            loadMore(vm.index, vm.pageIndex)
        }
    }

    private fun loadMore(pos: Int?, pageIndex: Int) {
        if (pos == null) {
            return
        }
        vm.getDetail(pageIndex, data.children[pos].id, object : OnBaseHttpListener<HomeDetailBean> {
            override fun onSuccess(t: HomeDetailBean) {
                bind.fgHomeRefresh.finishLoadMore()
                if (t.errorCode != 0) {
                    return
                }
                vm.pageIndex = vm.pageIndex + 1
                vm.data.addAll(t.data.datas)

                vm.adapter.notifyDataSetChanged()
            }

            override fun onError(e: Throwable) {
                //加载失败了
                bind.fgHomeRefresh.finishLoadMore()
            }
        })
    }

    //请求数据源
    private fun refresh(pos: Int?) {
        if (pos == null) {
            return
        }
        vm.getDetail(0, data.children[pos].id, object : OnBaseHttpListener<HomeDetailBean> {
            override fun onSuccess(t: HomeDetailBean) {
                bind.fgHomeRefresh.finishRefresh()
                if (t.errorCode != 0) {
                    bind.fgHomeTlRv.adapter = vm.adapter
                    return
                }
                vm.pageIndex = vm.pageIndex + 1
                vm.data.addAll(t.data.datas)
                bind.fgHomeTlRv.adapter = vm.adapter

            }

            override fun onError(e: Throwable) {
                //加载失败了
                bind.fgHomeTlRv.adapter = vm.adapter
                bind.fgHomeRefresh.finishRefresh()
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


    //当前显示第几个
    var index = 0
    //当前加载第几页
    var pageIndex = 0

    //数据源
    var data: MutableList<HomeDetailResultDataBean> = mutableListOf()

    //数据源
    val adapter: HomeDetailAdapter = HomeDetailAdapter(data)


    fun getDetail(
        curPage: Int,
        cid: Int, listener: OnBaseHttpListener<HomeDetailBean>
    ) {
        model.getDetail(lifecycle, curPage, cid, listener)
    }
}