package com.allens.ui.activity

import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivitySettinngBinding

class SettingAct : BaseMVVMAct<ActivitySettinngBinding,SettingModel,SettingVm>(){
    override fun initMVVMListener() {
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_settinng
    }

    override fun createModel(): SettingModel {
        return SettingModel()
    }

    override fun createVMClass(): Class<SettingVm> {
        return SettingVm::class.java
    }

}


class SettingModel : BaseModel


class SettingVm : BaseVM<SettingModel>()