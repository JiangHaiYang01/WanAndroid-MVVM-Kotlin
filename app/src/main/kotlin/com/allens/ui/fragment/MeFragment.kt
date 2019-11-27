package com.allens.ui.fragment

import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.FgMeBinding
import com.allens.ui.activity.LogInAct

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
        bind.vm = MeFgVM()


        //主界面
        bind.fgMeCl.setOnClickListener { startActivity(LogInAct::class.java) }
    }

}


class MeFgModel : BaseModel

class MeFgVM : BaseVM<MeFgModel>() {
    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"
    var defHeardImg = R.mipmap.fg_me_heard_def
}