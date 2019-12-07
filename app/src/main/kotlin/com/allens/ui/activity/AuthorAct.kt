package com.allens.ui.activity

import androidx.lifecycle.LifecycleOwner
import com.allens.bean.HomeDetailBean
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityAuthorBinding

class AuthorAct : BaseMVVMAct<ActivityAuthorBinding, AuthorModel, AuthorVM>() {
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
    }

    override fun initMVVMListener() {
    }

}


class AuthorModel : BaseModel {
    fun getDetailByAuthor(
        lifecycleOwner: LifecycleOwner,
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<HomeDetailBean>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycleOwner,
                HomeDetailBean::class.java,
                "article/list/$curPage/json?author=$author",
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


class AuthorVM : BaseVM<AuthorModel>() {
    fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<HomeDetailBean>
    ) {
        model.getDetailByAuthor(lifecycle, curPage, author, listener)
    }
}