package com.allens.ui

import android.graphics.Color
import android.widget.SeekBar
import com.allens.CustomTextView
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMFragment
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.filterAlphabet
import com.allens.tools.filterChinese
import com.allens.tools.getColorWithAlpha
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

        if (content.isNullOrEmpty()) {
            return
        }

        val left = content.replace("【", "&&&@&【")
        val right = left.replace("】", "】&&&@#")


        val split = right.split(Regex("&&&@&|&&&@#"))
        LogHelper.i("split------>$split")


        todoTv(split)



        bind.seek1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


                if (seekBar == null) {
                    return
                }
                val toFloat = seekBar.progress.toFloat()
                vm.textProgress = toFloat
                todoTv(split)
            }
        })

        bind.seek2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


                if (seekBar == null) {
                    return
                }
                val toFloat = seekBar.progress.toFloat()
                vm.meanProgress = toFloat
                todoTv(split)
            }
        })

        bind.seek3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


                if (seekBar == null) {
                    return
                }
                val toFloat = seekBar.progress.toFloat()
                vm.wordProgress = toFloat
                todoTv(split)
            }
        })

    }

    private fun todoTv(split: List<String>) {
        val tv = CustomTextView().create()
        split.forEach {
            if (it.matches(Regex("【(.*?)】"))) {
                val zh = filterChinese(it)
                val en = filterAlphabet(it)
                tv.addSection("【", getColorWithAlpha(vm.textProgress / 100, Color.BLACK))
                tv.addSection(en, getColorWithAlpha(vm.wordProgress/100,Color.BLACK))
                tv.addSection(zh, getColorWithAlpha(vm.meanProgress/100,Color.BLACK))
                tv.addSection("】", getColorWithAlpha(vm.textProgress / 100, Color.BLACK))
            } else {
                tv.addSection(it, getColorWithAlpha(vm.textProgress / 100, Color.BLACK))
            }
        }
        tv.init(bind.actDetailTv) { msg, index ->
            LogHelper.i("click item $msg")
        }
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


class WordModel1Vm : BaseVM<WordModel1Model>() {
    val data = mutableListOf<String>()

    var textProgress = 100f
    var wordProgress = 100f
    var meanProgress = 100f
}