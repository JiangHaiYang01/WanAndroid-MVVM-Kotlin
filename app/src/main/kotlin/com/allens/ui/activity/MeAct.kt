package com.allens.ui.activity

import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityMeBinding

class MeAct : BaseMVVMAct<ActivityMeBinding, MeActModel, MeActVM>() {
    override fun initMVVMListener() {

    }

    override fun getContentViewId(): Int {
        return R.layout.activity_me
    }


    override fun createModel(): MeActModel {
        return MeActModel()
    }

    override fun createVMClass(): Class<MeActVM> {
        return MeActVM::class.java
    }

}

class MeActModel : BaseModel {

}


class MeActVM : BaseVM<MeActModel>() {

}