package com.allens.ui

import android.widget.ImageView
import android.widget.TextView
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_base.tools.getFileContent
import com.example.lib_aws.R
import com.example.lib_aws.databinding.ActivityAwsDetailBinding
import java.io.File

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


        //获取文件内容
        val path = intent.getStringExtra(PATH)
        if (path.isNullOrEmpty()) {
            return
        }
        val content = vm.getContent(path)
        LogHelper.i("读取到的文件内容 $content")
        bind.actDetailTv.text = content

    }

}


class WordDetailModel : BaseModel(), WordDetailImpl {

    override fun getContent(path: String): String? {
        val stringBuffer = StringBuffer()
        File(path).readLines().forEach {
            LogHelper.i(it)
            stringBuffer.append(it)
            stringBuffer.append("\n")
        }
        return stringBuffer.toString()
    }
}


class WordDetailVm : BaseVM<WordDetailModel>(), WordDetailImpl {
    override fun getContent(path: String): String? {
        return model.getContent(path)
    }

}

interface WordDetailImpl {
    fun getContent(path: String): String?
}