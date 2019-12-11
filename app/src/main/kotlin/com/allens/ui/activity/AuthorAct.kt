package com.allens.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import com.allens.LogHelper
import com.allens.bean.author_detail.AuthorDetail
import com.allens.bean.author_detail.DataX
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityAuthorBinding
import com.allens.ui.adapter.AuthorDetailAdapter

class AuthorAct : BaseMVVMAct<ActivityAuthorBinding, AuthorModel, AuthorVM>(),
    AuthorDetailAdapter.OnDetailAdapterListener {

    companion object {
        const val Author = "author"
    }


    private lateinit var author: String

    override fun createModel(): AuthorModel {
        return AuthorModel()
    }

    override fun createVMClass(): Class<AuthorVM> {
        return AuthorVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_author
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        author = intent.getStringExtra(Author) ?: "未知"
        bind.title.setTitle(author)
        bind.title.setBack(this)

        vm.adapter.setOnDetailAdapterListener(this)
        refresh()
    }

    private fun refresh() {
        //自动刷新
        bind.fgFindRefresh.autoRefresh()
        bind.fgFindRefresh.setOnRefreshListener {
            LogHelper.i("收藏 界面  下拉刷新")
            vm.pageIndex = 0
            vm.getDetailByAuthor(0, author, object : OnBaseHttpListener<AuthorDetail> {
                override fun onError(e: Throwable) {
                    bind.fgFindRefresh.finishRefresh()
                }

                override fun onSuccess(t: AuthorDetail) {
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
            vm.getDetailByAuthor(vm.pageIndex, author, object : OnBaseHttpListener<AuthorDetail> {
                override fun onError(e: Throwable) {
                    bind.fgFindRefresh.finishLoadMore()
                }

                override fun onSuccess(t: AuthorDetail) {
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

    override fun onClickHomeDetailItem(item: DataX) {
        val bundle = Bundle()
        bundle.putString(WebAct.WEB_URL, item.link)
        bundle.putInt(WebAct.WEB_ID, item.id)
        val intent = Intent(this, WebAct::class.java)
        intent.putExtras(bundle)
        startActivity(WebAct::class.java, bundle)
    }
}


class AuthorModel : BaseModel(), AuthorModelImpl {
    override fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<AuthorDetail>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                AuthorDetail::class.java,
                "article/list/$curPage/json?author=$author",
                object : OnHttpListener<AuthorDetail>() {
                    override fun onSuccess(t: AuthorDetail) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class AuthorVM : BaseVM<AuthorModel>(), AuthorModelImpl {


    var pageIndex: Int = 0

    var data = mutableListOf<DataX>()

    var adapter = AuthorDetailAdapter(data)


    override fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<AuthorDetail>
    ) {
        model.getDetailByAuthor(curPage, author, listener)
    }
}

interface AuthorModelImpl {
    fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<AuthorDetail>
    )
}