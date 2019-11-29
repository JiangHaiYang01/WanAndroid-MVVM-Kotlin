package com.allens.ui.fragment

import androidx.lifecycle.Observer
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.status.UserStatus
import com.allens.tools.R
import com.allens.tools.databinding.FgMeBinding
import com.allens.ui.activity.LogInAct
import java.util.logging.Logger

class MeFragment : BaseMVVMFragment<FgMeBinding, MeFgModel, MeFgVM>() {
    override fun createModel(): MeFgModel {
        return MeFgModel()
    }

    override fun createVMClass(): Class<MeFgVM> {
        return MeFgVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_me
    }

    override fun initMVVMListener() {
        bind.vm = vm
        bind.user = UserStatus


        //主界面
        bind.fgMeCl.setOnClickListener { startActivity(LogInAct::class.java) }


        //是否登录
        UserStatus.isLogIn.observe(this, Observer {
            LogHelper.i("me fragment 是否登录 ${it}")
        })

    }

}


class MeFgModel : BaseModel

class MeFgVM : BaseVM<MeFgModel>() {
    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"


}