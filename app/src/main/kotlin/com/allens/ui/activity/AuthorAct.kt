package com.allens.ui.activity

import androidx.lifecycle.LifecycleOwner
import com.allens.bean.home_system_detail.DataX
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


class AuthorModel : BaseModel(), AuthorModelImpl {
    override fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<DataX>
    ) {
        HttpTool.xHttp
            .doGet(
                lifecycle,
                DataX::class.java,
                "article/list/$curPage/json?author=$author",
                object : OnHttpListener<DataX>() {
                    override fun onSuccess(t: DataX) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}


class AuthorVM : BaseVM<AuthorModel>(), AuthorModelImpl {
    override fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<DataX>
    ) {
        model.getDetailByAuthor(curPage, author, listener)
    }
}

interface AuthorModelImpl {
    fun getDetailByAuthor(
        curPage: Int,
        author: String,
        listener: OnBaseHttpListener<DataX>
    )
}