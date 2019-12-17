package com.allens.ui

import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.example.lib_aws.R
import com.example.lib_aws.databinding.FgWordDetail2Binding

/**

 * @Author allens
 * @Date 2019-12-17-15:16
 * @Email 18856907654@163.com
 */
class WordModel2Fg(content: String?) : BaseMVVMFragment<FgWordDetail2Binding, WordModel2Model, WordModel2Vm>() {
    override fun initMVVMBind() {
        bind.vm = vm

    }

    override fun initMVVMListener() {
    }

    override fun getContentViewId(): Int {
        return R.layout.fg_word_detail_2

    }

    override fun createModel(): WordModel2Model {
        return WordModel2Model()
    }

    override fun createVMClass(): Class<WordModel2Vm> {
        return WordModel2Vm::class.java
    }

}


class WordModel2Model : BaseModel() {

}


class WordModel2Vm : BaseVM<WordModel2Model>()