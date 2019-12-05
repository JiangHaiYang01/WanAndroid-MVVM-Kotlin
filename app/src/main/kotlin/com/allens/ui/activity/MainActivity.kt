package com.allens.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_base.tools.TabHelper
import com.allens.tools.R
import com.allens.tools.databinding.ActivityMainBinding
import com.allens.ui.fragment.FindFragment
import com.allens.ui.fragment.HomeFragment
import com.allens.ui.fragment.MeFragment
import com.google.android.material.tabs.TabLayout
import java.util.*

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

        val list = arrayListOf("首页", "沸点", "发现", "我的")
        list.forEach {
            val tab = bind.actMainTl.newTab()
            tab.setCustomView(R.layout.view_act_main_tab)
            bind.actMainTl.addTab(tab)
        }

        bind.actMainTl.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {


            }
        })

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
    val selectColor = Color.parseColor("#0088FF")
    //未选中颜色
    val defColor = Color.parseColor("#333333")

}
