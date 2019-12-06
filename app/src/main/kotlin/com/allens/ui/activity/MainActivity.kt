package com.allens.ui.activity

import android.graphics.Color
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_base.tools.TabHelper
import com.allens.tools.R
import com.allens.tools.databinding.ActivityMainBinding
import com.allens.ui.fragment.FindFragment
import com.allens.ui.fragment.HomeFragment
import com.allens.ui.fragment.MeFragment

class MainActivity : BaseMVVMAct<ActivityMainBinding, MainModel, MainVM>() {
    override fun createModel(): MainModel {

        return MainModel()
    }

    override fun createVMClass(): Class<MainVM> {
        return MainVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {

        TabHelper(
            supportFragmentManager,
            listOf(HomeFragment(), HomeFragment(), FindFragment(), MeFragment()),
            bind.actMainRg,
            R.id.act_main_fl
        ).showTabToFragment()
    }

}

class MainModel : BaseModel

class MainVM : BaseVM<MainModel>() {
    //选中颜色
    val selectColor = Color.parseColor("#333333")
    //未选中颜色
    val defColor = Color.parseColor("#AAB0BE")

    //tab宽高
    val tabHeight = 80
    val tabWidth = 80

}
