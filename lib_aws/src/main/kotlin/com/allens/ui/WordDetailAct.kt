package com.allens.ui

import android.widget.ImageView
import android.widget.TextView
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.example.lib_aws.R
import com.example.lib_aws.databinding.ActivityAwsDetailBinding

/**

 * @Author allens
 * @Date 2019-12-17-11:57
 * @Email 18856907654@163.com
 */
class WordDetailAct : BaseMVVMAct<ActivityAwsDetailBinding, WordDetailModel, WordDetailVm>() {

    companion object {
        const val PATH = "path"
    }

    override fun createModel(): WordDetailModel {
        return WordDetailModel()
    }

    override fun createVMClass(): Class<WordDetailVm> {
        return WordDetailVm::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_aws_detail
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {
        bind.actAwsTitle.findViewById<TextView>(R.id.include_tv_title).text = "单词详情"
        bind.actAwsTitle.findViewById<ImageView>(R.id.include_img_back)
            .setOnClickListener { finish() }
    }

}


class WordDetailModel : BaseModel(){

}



class WordDetailVm : BaseVM<WordDetailModel>()