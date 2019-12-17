package com.allens.ui

import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.filterAlphabet
import com.allens.tools.filterChinese
import com.example.lib_aws.R
import com.example.lib_aws.databinding.FgWordDetail2Binding
import com.example.spark_word.adapter.WordItemAdapter
import org.apache.commons.logging.Log

/**

 * @Author allens
 * @Date 2019-12-17-15:16
 * @Email 18856907654@163.com
 */
class WordModel2Fg(private val content: String?) :
    BaseMVVMFragment<FgWordDetail2Binding, WordModel2Model, WordModel2Vm>() {
    override fun initMVVMBind() {
        bind.vm = vm

    }

    override fun initMVVMListener() {

        if (content.isNullOrEmpty()) {
            return
        }

        bind.fgDetailRy.adapter = vm.adapter

        val regex = Regex("【(.*?)】")
        val matches = regex.findAll(content)
        matches.toList().forEach {
            val left = it.value.replace("【", "")
            val data = left.replace("】", "")
            val zh = filterChinese(data)
            val en = filterAlphabet(data)
            LogHelper.i("en : $en  zh: $zh")

            vm.data.add(ItemWord(en, zh))
        }

        vm.adapter.notifyDataSetChanged()

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


class WordModel2Vm : BaseVM<WordModel2Model>() {
    val data = mutableListOf<ItemWord>()

    val adapter = WordItemAdapter(data)
}

data class ItemWord(
    val en: String,
    val zh: String
)