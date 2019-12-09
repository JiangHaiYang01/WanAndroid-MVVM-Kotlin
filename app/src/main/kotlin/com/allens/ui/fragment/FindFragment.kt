package com.allens.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.allens.model_base.base.BaseFragment
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.FgFindBinding
import com.allens.tools.databinding.FgHomeBinding

//发现界面
class FindFragment : BaseMVVMFragment<FgFindBinding, FindModel, FindVM>() {
    override fun initMVVMListener() {
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_find
    }

    override fun createModel(): FindModel {
        return FindModel()
    }

    override fun createVMClass(): Class<FindVM> {
        return FindVM::class.java
    }

    override fun initMVVMBind() {
    }
}


class FindModel : BaseModel()

class FindVM : BaseVM<FindModel>()