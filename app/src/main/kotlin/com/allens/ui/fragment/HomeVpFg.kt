package com.allens.ui.fragment

import android.widget.TextView
import com.allens.LogHelper
import com.allens.bean.SystemResultBean
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.FgHomeVpBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.BaseOnTabSelectedListener

class HomeVpFg(private val data: SystemResultBean) :
    BaseMVVMFragment<FgHomeVpBinding, HomeVPModel, HomeVPVM>() {
    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {

        //添加子集
        for (index in data.children.indices) {
            val tab = bind.fgHomeVpTlParent.newTab()
            tab.setCustomView(R.layout.view_custom_tab)
            val textView = tab.customView?.findViewById<TextView>(R.id.view_custom_tab_tv)
            textView?.text = data.children[index].name
            setTabSelect(tab)
            bind.fgHomeVpTlParent.addTab(tab)
        }


        //选中事件监听
        bind.fgHomeVpTlParent.addOnTabSelectedListener(object :
            BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(ta1: TabLayout.Tab?) {
                LogHelper.i(" onTabReselected =================> ${ta1?.position}     ${ta1?.isSelected}")
            }

            override fun onTabUnselected(ta1: TabLayout.Tab?) {
                LogHelper.i(" onTabUnselected =================> ${ta1?.position}     ${ta1?.isSelected}")
                setTabSelect(ta1)
            }

            override fun onTabSelected(ta1: TabLayout.Tab?) {
                LogHelper.i(" onTabSelected =================> ${ta1?.position}     ${ta1?.isSelected}")
                setTabSelect(ta1)
            }
        })
    }


    fun setTabSelect(tab: TabLayout.Tab?) {
        if (tab == null) {
            return
        }
        val textView = tab.customView?.findViewById<TextView>(R.id.view_custom_tab_tv)
        if (tab.isSelected) {
            textView?.setBackgroundResource(R.drawable.bg_tab_blue)
        } else {
            textView?.setBackgroundResource(R.drawable.bg_tab_gray)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_home_vp
    }

    override fun createModel(): HomeVPModel {
        return HomeVPModel()
    }

    override fun createVMClass(): Class<HomeVPVM> {
        return HomeVPVM::class.java
    }

}


class HomeVPModel : BaseModel {

}


class HomeVPVM : BaseVM<HomeVPModel>()