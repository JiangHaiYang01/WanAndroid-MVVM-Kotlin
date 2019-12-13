package com.allens.ui.fragment.hot.hot_detail

import android.os.Bundle
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
import com.allens.tools.databinding.FgHotNewProjectBinding
import com.allens.tools.databinding.FgHotProjectBinding
import com.allens.ui.activity.WebAct
import com.allens.ui.adapter.ProjectDetailAdapter
import com.google.android.material.tabs.TabLayout

//最新项目
class NewProjectFg : BaseMVVMFragment<FgHotNewProjectBinding, NewProjectModel, NewProjectVM>() {
    override fun createModel(): NewProjectModel {
        return NewProjectModel()
    }

    override fun createVMClass(): Class<NewProjectVM> {
        return NewProjectVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_hot_new_project
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {


        //自动刷新
        bind.fgHomeRefresh.autoRefresh()

        addRefresh()

        vm.adapter.setOnDetailAdapterListener(object :
            ProjectDetailAdapter.OnDetailAdapterListener {
            override fun onClickHomeDetailItem(item: DataX) {
                val bundle = Bundle()
                bundle.putString(WebAct.WEB_URL, item.link)
                bundle.putInt(WebAct.WEB_ID, item.id)
                startActivity(WebAct::class.java, bundle)
            }
        })
    }

    private fun addRefresh() {
        bind.fgHomeRefresh.setOnRefreshListener {
            refresh()
        }

        bind.fgHomeRefresh.setOnLoadMoreListener {
            loadMore(vm.pageIndex)
        }
    }

    private fun loadMore(pageIndex: Int) {
        vm.getOfficialDetail(pageIndex, object : OnBaseHttpListener<ProjectDetailBean> {
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

    private fun refresh() {
        vm.pageIndex = 0
        vm.getOfficialDetail(vm.pageIndex, object : OnBaseHttpListener<ProjectDetailBean> {
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
                vm.data.clear()
                vm.data.addAll(t.data.datas)
                bind.fgHomeTlRv.adapter = vm.adapter
                vm.pageIndex = vm.pageIndex + 1
            }
        })
    }
}


class NewProjectModel : BaseModel(), NewProjectModelImpl {

    override fun getOfficialDetail(
        pageIndex: Int,
        listener: OnBaseHttpListener<ProjectDetailBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                ProjectDetailBean::class.java,
                "article/listproject/$pageIndex/json",
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


class NewProjectVM : BaseVM<NewProjectModel>(), NewProjectModelImpl {


    //当前加载第几页
    var pageIndex = 0

    //数据源
    var data: MutableList<DataX> = mutableListOf()

    //数据源
    val adapter: ProjectDetailAdapter = ProjectDetailAdapter(data)


    override fun getOfficialDetail(
        pageIndex: Int,
        listener: OnBaseHttpListener<ProjectDetailBean>
    ) {
        model.getOfficialDetail(pageIndex, listener)
    }

}


interface NewProjectModelImpl {

    fun getOfficialDetail(
        pageIndex: Int,
        listener: OnBaseHttpListener<ProjectDetailBean>
    )
}