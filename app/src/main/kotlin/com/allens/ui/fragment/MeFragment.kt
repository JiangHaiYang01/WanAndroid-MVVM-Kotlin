package com.allens.ui.fragment

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.allens.LogHelper
import com.allens.data.dto.FgItemDto
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.status.UserStatus
import com.allens.tools.R
import com.allens.tools.databinding.FgMeBinding
import com.allens.ui.activity.LogInAct
import com.allens.ui.activity.MeAct
import com.allens.ui.activity.SettingAct
import com.allens.ui.adapter.MeFragmentItemAdapter
import java.util.logging.Logger

class MeFragment : BaseMVVMFragment<FgMeBinding, MeFgModel, MeFgVM>(),
    MeFragmentItemAdapter.OnMeFragmentItemClickListener {
    override fun createModel(): MeFgModel {
        return MeFgModel()
    }

    override fun createVMClass(): Class<MeFgVM> {
        return MeFgVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_me
    }

    override fun initMVVMBind() {
        bind.vm = vm
        bind.user = UserStatus

    }

    override fun initMVVMListener() {


        //个人中心 // 登录
        bind.fgMeCl.setOnClickListener {
            if (UserStatus.isLogIn.value == true) {
                startActivity(MeAct::class.java)
            } else {
                startActivity(LogInAct::class.java)
            }

        }

        //列表 adapter
        bind.rfMeRy.adapter = MeFragmentItemAdapter(vm.model.getItemData(), this)
        bind.rfMeRyBottom.adapter = MeFragmentItemAdapter(vm.model.getItemBottomData(), this)


    }

    //点击事件
    override fun onFragmentItemClick(pos: Int, item: FgItemDto) {
        when (item.tag) {
            //收藏集
            2->{
//                startActivity(CollectionAct::class.java)
            }
            //设置
            6 -> {
                if (UserStatus.isLogIn.value == true) {
                    startActivity(SettingAct::class.java)
                } else {
                    startActivity(LogInAct::class.java)
                }
            }
        }
    }

}


class MeFgModel : BaseModel(), MeFgModelImpl {
    override fun getItemData(): MutableList<FgItemDto> {
        return mutableListOf(
            FgItemDto(R.drawable.fg_me_notify, "消息中心", false, 0),
            FgItemDto(R.drawable.fg_me_give, "我赞过的", false, 1),
            FgItemDto(R.drawable.fg_me_collectionset, "收藏集", false, 2),
            FgItemDto(R.drawable.fg_me_history, "阅读过的文章", false, 3),
            FgItemDto(R.drawable.fg_me_tag, "标签管理", false, 4)
        )
    }

    override fun getItemBottomData(): MutableList<FgItemDto> {
        return mutableListOf(
            FgItemDto(R.drawable.fg_me_options, "意见反馈", false, 5),
            FgItemDto(R.drawable.fg_me_setting, "设置", false, 6)
        )
    }

}

class MeFgVM : BaseVM<MeFgModel>() {
    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"


}

interface MeFgModelImpl {
    fun getItemData(): MutableList<FgItemDto>
    fun getItemBottomData(): MutableList<FgItemDto>
}