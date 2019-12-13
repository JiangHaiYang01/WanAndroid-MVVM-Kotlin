package com.allens.ui.activity

import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityShareUserDetailBinding

class ShareUserDetailAct :
    BaseMVVMAct<ActivityShareUserDetailBinding, ShareUserDetailModel, ShareUserDetailVM>() {


    companion object{
        const val USERID = "userId"
    }
    override fun createModel(): ShareUserDetailModel {
        return ShareUserDetailModel()
    }

    override fun createVMClass(): Class<ShareUserDetailVM> {
        return ShareUserDetailVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_share_user_detail
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
    }
}

class ShareUserDetailModel : BaseModel()


class ShareUserDetailVM : BaseVM<ShareUserDetailModel>() {
    //横图URL
    val defUrl =
        "https://c-ssl.duitang.com/uploads/item/201811/13/20181113104440_5w4kR.thumb.700_0.jpeg"

    var heardImgUrl = "http://static.runoob.com/images/demo/demo1.jpg"


}