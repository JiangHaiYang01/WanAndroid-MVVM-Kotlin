package com.allens.ui

import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.example.lib_aws.R
import com.example.lib_aws.databinding.FgWordDetail1Binding

/**

 * @Author allens
 * @Date 2019-12-17-15:16
 * @Email 18856907654@163.com
 */
class WordModel1Fg(private val content: String?) :
    BaseMVVMFragment<FgWordDetail1Binding, WordModel1Model, WordModel1Vm>() {
    override fun initMVVMBind() {
        bind.vm = vm

    }

    override fun initMVVMListener() {
        bind.actDetailTv.text = content
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_word_detail_1

    }

    override fun createModel(): WordModel1Model {
        return WordModel1Model()
    }

    override fun createVMClass(): Class<WordModel1Vm> {
        return WordModel1Vm::class.java
    }

}


class WordModel1Model : BaseModel() {

}


class WordModel1Vm : BaseVM<WordModel1Model>()