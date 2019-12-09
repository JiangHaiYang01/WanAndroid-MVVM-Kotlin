package com.allens.ui.fragment

import androidx.fragment.app.Fragment
import com.allens.model_base.base.BaseFragment
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.FgHotBinding
import com.allens.ui.adapter.HotFgPagerAdapter
import com.allens.ui.fragment.hot_detail.OfficialFg
import java.util.ArrayList

class HotFragment : BaseMVVMFragment<FgHotBinding, HotModel, HotFgVM>() {
    override fun createModel(): HotModel {
        return HotModel()
    }

    override fun createVMClass(): Class<HotFgVM> {
        return HotFgVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_hot
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        addTab()
        addViewPager()
    }

    private fun addViewPager() {
        bind.fgHotVp.adapter = HotFgPagerAdapter(vm.fragList, vm.tabList, childFragmentManager)
        bind.fgHotTlParent.setupWithViewPager(bind.fgHotVp);
    }

    private fun addTab() {
        vm.tabList.forEach {
            bind.fgHotTlParent.addTab(bind.fgHotTlParent.newTab().setText(it))
        }
    }
}


class HotModel : BaseModel()


class HotFgVM : BaseVM<HotModel>() {


    var tabList = mutableListOf<String>(
        "关注",
        "推荐",
        "热门",
        "公众号",
        "开源推荐",
        "常用网站",
        "问答"
    )
    var fragList = mutableListOf<BaseFragment>(
        OfficialFg(),
        OfficialFg(),
        OfficialFg(),
        OfficialFg(),
        OfficialFg(),
        OfficialFg(),
        OfficialFg())
}