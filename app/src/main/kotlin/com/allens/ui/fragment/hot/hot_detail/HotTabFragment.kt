package com.allens.ui.fragment.hot.hot_detail

import android.content.Intent
import android.os.Bundle
import com.allens.LogHelper
import com.allens.bean.baner.BannerBean
import com.allens.bean.home_detail.DataX
import com.allens.bean.home_detail.HomeDetailBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.FgHotTabBinding
import com.allens.ui.activity.WebAct
import com.allens.ui.adapter.FindDetailAdapter

//推荐热门
class HotTabFragment : BaseMVVMFragment<FgHotTabBinding, HotTabModel, HotTabVM>(),
    FindDetailAdapter.OnHomeDetailAdapterListener {


    override fun getContentViewId(): Int {
        return R.layout.fg_hot_tab
    }

    override fun createModel(): HotTabModel {
        return HotTabModel()
    }

    override fun createVMClass(): Class<HotTabVM> {
        return HotTabVM::class.java
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        vm.adapter.setOnHomeDetailAdapterListener(this)
        refresh()
    }

    private fun refresh() {
        //自动刷新
        bind.fgFindRefresh.autoRefresh()
        bind.fgFindRefresh.setOnRefreshListener {
            LogHelper.i("推荐热门 界面  下拉刷新")
            vm.pageIndex = 0
            vm.getHome(0, object : OnBaseHttpListener<HomeDetailBean> {
                override fun onError(e: Throwable) {
                    bind.fgFindRefresh.finishRefresh()
                }

                override fun onSuccess(t: HomeDetailBean) {
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
            LogHelper.i("推荐热门 界面  加载更多 pageIndex ${vm.pageIndex}")
            vm.getHome(vm.pageIndex, object : OnBaseHttpListener<HomeDetailBean> {
                override fun onError(e: Throwable) {
                    bind.fgFindRefresh.finishLoadMore()
                }

                override fun onSuccess(t: HomeDetailBean) {
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
        LogHelper.i("推荐热门 fg 点击 item ${item.author} url ${item.link}")

        val bundle = Bundle()
        bundle.putString(WebAct.WEB_URL, item.link)
        bundle.putInt(WebAct.WEB_ID, item.id)
        val intent = Intent(activity, WebAct::class.java)
        intent.putExtras(bundle)
        startActivity(WebAct::class.java, bundle)
    }

}

class HotTabModel : BaseModel(), HotTabModelImpl {
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

    override fun getHome(pageIndex: Int, listener: OnBaseHttpListener<HomeDetailBean>) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                HomeDetailBean::class.java,
                "article/list/$pageIndex/json",
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

class HotTabVM : BaseVM<HotTabModel>(), HotTabModelImpl {

    var pageIndex: Int = 0

    var data = mutableListOf<DataX>()

    var adapter = FindDetailAdapter(data)


    override fun getBanner(listener: OnBaseHttpListener<BannerBean>) {
        model.getBanner(listener)
    }

    override fun getHome(pageIndex: Int, listener: OnBaseHttpListener<HomeDetailBean>) {
        model.getHome(pageIndex, listener)
    }

}


interface HotTabModelImpl {
    fun getBanner(listener: OnBaseHttpListener<BannerBean>)
    fun getHome(pageIndex: Int, listener: OnBaseHttpListener<HomeDetailBean>)
}