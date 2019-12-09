package com.allens.ui.fragment.hot.hot_detail

import android.widget.TextView
import com.allens.LogHelper
import com.allens.bean.project_detail.DataX
import com.allens.bean.project_detail.ProjectDetailBean
import com.allens.bean.project_tab.ProjectTabBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHotProjectBinding
import com.allens.ui.adapter.ProjectDetailAdapter
import com.google.android.material.tabs.TabLayout

//开源项目
class ProjectFg : BaseMVVMFragment<FgHotProjectBinding, ProjectModel, ProjectVM>() {
    override fun createModel(): ProjectModel {
        return ProjectModel()
    }

    override fun createVMClass(): Class<ProjectVM> {
        return ProjectVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_hot_project
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {


        vm.getProjectTab(object : OnBaseHttpListener<ProjectTabBean> {
            override fun onError(e: Throwable) {

            }

            override fun onSuccess(t: ProjectTabBean) {
                if (t.errorCode != 0) {
                    return
                }

                addOfficialTab(t)
                //自动刷新
                bind.fgHomeRefresh.autoRefresh()
                //添加选择器
                addOfficialTabSelect(t)
                //下拉刷新  加载更多
                addRefresh(t);
            }
        })
    }

    private fun addRefresh(t: ProjectTabBean) {
        bind.fgHomeRefresh.setOnRefreshListener {
            LogHelper.i("开源项目 下拉刷新 ----> ${t.data[vm.index].name} ")
            refresh(vm.index, t)
        }

        bind.fgHomeRefresh.setOnLoadMoreListener {
            LogHelper.i("开源项目 加载更多 ----> ${t.data[vm.index].name} pageIndex ${vm.pageIndex}")
            loadMore(vm.index, vm.pageIndex, t)
        }
    }

    private fun loadMore(index: Int, pageIndex: Int, t: ProjectTabBean) {
        val id = t.data[index].id
        vm.getOfficialDetail(id, pageIndex, object : OnBaseHttpListener<ProjectDetailBean> {
            override fun onError(e: Throwable) {
                bind.fgHomeRefresh.finishLoadMore()
            }

            override fun onSuccess(t: ProjectDetailBean) {
                bind.fgHomeRefresh.finishLoadMore()
                if (t.errorCode != 0) {
                    return
                }
                if (t.data.datas.isNullOrEmpty()) {
                    return
                }
                vm.data.addAll(t.data.datas)
                vm.adapter.notifyDataSetChanged()
                vm.pageIndex = vm.pageIndex + 1
            }
        })
    }

    private fun refresh(index: Int, t: ProjectTabBean) {
        vm.pageIndex = 0
        val id = t.data[index].id
        vm.getOfficialDetail(id, vm.pageIndex, object : OnBaseHttpListener<ProjectDetailBean> {
            override fun onError(e: Throwable) {
                bind.fgHomeRefresh.finishRefresh()
                bind.fgHomeTlRv.adapter = vm.adapter
            }

            override fun onSuccess(t: ProjectDetailBean) {
                bind.fgHomeRefresh.finishRefresh()
                if (t.errorCode != 0) {
                    bind.fgHomeTlRv.adapter = vm.adapter
                    return
                }
                vm.data.addAll(t.data.datas)
                bind.fgHomeTlRv.adapter = vm.adapter
                vm.pageIndex = vm.pageIndex + 1
            }
        })
    }


    private fun addOfficialTabSelect(t: ProjectTabBean) {
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

    private fun addOfficialTab(t: ProjectTabBean) {
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


class ProjectModel : BaseModel(), ProjectModelImpl {
    override fun getProjectTab(
        listener: OnBaseHttpListener<ProjectTabBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                ProjectTabBean::class.java,
                "project/tree/json",
                object : OnHttpListener<ProjectTabBean>() {
                    override fun onSuccess(t: ProjectTabBean) {
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
        listener: OnBaseHttpListener<ProjectDetailBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                ProjectDetailBean::class.java,
                "project/list/$pageIndex/json?cid=$id",
                object : OnHttpListener<ProjectDetailBean>() {
                    override fun onSuccess(t: ProjectDetailBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class ProjectVM : BaseVM<ProjectModel>(), ProjectModelImpl {


    //当前显示第几个
    var index = 0
    //当前加载第几页
    var pageIndex = 0

    //数据源
    var data: MutableList<DataX> = mutableListOf()

    //数据源
    val adapter: ProjectDetailAdapter = ProjectDetailAdapter(data)


    override fun getProjectTab(listener: OnBaseHttpListener<ProjectTabBean>) {
        model.getProjectTab(listener)
    }

    override fun getOfficialDetail(
        id: Int,
        pageIndex: Int,
        listener: OnBaseHttpListener<ProjectDetailBean>
    ) {
        model.getOfficialDetail(id, pageIndex, listener)
    }

}


interface ProjectModelImpl {
    fun getProjectTab(
        listener: OnBaseHttpListener<ProjectTabBean>
    )

    fun getOfficialDetail(
        id: Int,
        pageIndex: Int,
        listener: OnBaseHttpListener<ProjectDetailBean>
    )
}