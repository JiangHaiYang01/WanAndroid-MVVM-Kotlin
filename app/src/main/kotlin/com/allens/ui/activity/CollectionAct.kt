package com.allens.ui.activity

import android.content.Intent
import android.os.Bundle
import com.allens.LogHelper
import com.allens.bean.collection_detail.CollectionDetailBean
import com.allens.bean.collection_detail.DataX
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityCollectionBinding
import com.allens.ui.adapter.CollectionDetailAdapter

class CollectionAct : BaseMVVMAct<ActivityCollectionBinding, CollectionModel, CollectionVM>(),
    CollectionDetailAdapter.OnHomeDetailAdapterListener {
    override fun createModel(): CollectionModel {
        return CollectionModel()
    }

    override fun createVMClass(): Class<CollectionVM> {
        return CollectionVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_collection
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {

        bind.title.setTitle(resources.getString(R.string.act_title_collection))
        bind.title.setBack(this)

        vm.adapter.setOnHomeDetailAdapterListener(this)
        refresh()
    }

    private fun refresh() {
        //自动刷新
        bind.fgFindRefresh.autoRefresh()
        bind.fgFindRefresh.setOnRefreshListener {
            LogHelper.i("收藏 界面  下拉刷新")
            vm.pageIndex = 0
            vm.getDetail(0, object : OnBaseHttpListener<CollectionDetailBean> {
                override fun onError(e: Throwable) {
                    bind.fgFindRefresh.finishRefresh()
                }

                override fun onSuccess(t: CollectionDetailBean) {
                    bind.fgFindRefresh.finishRefresh()
                    if (t.errorCode != 0) {
                        return
                    }
                    vm.data.clear()
                    vm.data.addAll(t.data.datas)
                    bind.fgHomeTlRv.adapter = vm.adapter
                    vm.pageIndex = vm.pageIndex + 1

                }
            })
        }

        bind.fgFindRefresh.setOnLoadMoreListener {
            LogHelper.i("收集 界面  加载更多 pageIndex ${vm.pageIndex}")
            vm.getDetail(vm.pageIndex, object : OnBaseHttpListener<CollectionDetailBean> {
                override fun onError(e: Throwable) {
                    bind.fgFindRefresh.finishLoadMore()
                }

                override fun onSuccess(t: CollectionDetailBean) {
                    bind.fgFindRefresh.finishLoadMore()
                    if (t.errorCode != 0) {
                        return
                    }
                    vm.data.addAll(t.data.datas)
                    vm.adapter.notifyDataSetChanged()
                    vm.pageIndex = vm.pageIndex + 1

                }
            })
        }
    }

    override fun onClickHomeDetailAuthor(item: DataX) {
    }

    override fun onClickHomeDetailItem(item: DataX) {
        LogHelper.i("收藏 点击 item ${item.author} url ${item.link} id ${item.id}")
        val bundle = Bundle()
        bundle.putString(WebAct.WEB_URL, item.link)
        bundle.putInt(WebAct.WEB_ID, item.id)
        val intent = Intent(this, WebAct::class.java)
        intent.putExtras(bundle)
        startActivity(WebAct::class.java, bundle)
    }
}


class CollectionModel : BaseModel(), CollectionModelImpl {

    override fun getDetail(pageIndex: Int, listener: OnBaseHttpListener<CollectionDetailBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                CollectionDetailBean::class.java,
                "lg/collect/list/$pageIndex/json",
                object : OnHttpListener<CollectionDetailBean>() {
                    override fun onSuccess(t: CollectionDetailBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}

class CollectionVM : BaseVM<CollectionModel>(), CollectionModelImpl {

    var pageIndex: Int = 0

    var data = mutableListOf<DataX>()

    var adapter = CollectionDetailAdapter(data)


    override fun getDetail(pageIndex: Int, listener: OnBaseHttpListener<CollectionDetailBean>) {
        model.getDetail(pageIndex, listener)
    }
}


interface CollectionModelImpl {
    fun getDetail(pageIndex: Int, listener: OnBaseHttpListener<CollectionDetailBean>)
}