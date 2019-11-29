package com.allens.ui.fragment

import androidx.lifecycle.MediatorLiveData
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
        bind.fgMeImgHeard.setOnClickListener { startActivity(LogInAct::class.java) }

        //设置
        bind.fgMeImgHeard.setOnClickListener {
            LogHelper.i("点击进入设置界面")
        }

        bind.fgMeStar.setInfo(resources.getString(R.string.fg_me_tv_star))
        bind.fgMeRank.setInfo(resources.getString(R.string.fg_me_tv_ranking))
        bind.fgMeGrade.setInfo(resources.getString(R.string.fg_me_tv_grade))


    }

}


class MeFgModel : BaseModel

class MeFgVM : BaseVM<MeFgModel>() {
    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"

    //积分
    var integral = MediatorLiveData<String>().apply { value = "0" }
    //排行
    var ranking = MediatorLiveData<String>().apply { value = "0" }
    //等级
    var grade = MediatorLiveData<String>().apply { value = "0" }


}