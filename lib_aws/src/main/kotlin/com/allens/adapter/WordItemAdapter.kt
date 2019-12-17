package com.example.spark_word.adapter

import com.allens.ui.ItemWord
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_aws.R


//使用 kotlin  写adapter
class WordItemAdapter(
    data: MutableList<ItemWord>
) :
    BaseQuickAdapter<ItemWord, BaseViewHolder>(R.layout.item_word_model_2, data) {
    override fun convert(helper: BaseViewHolder?, item: ItemWord?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }
        helper.itemView.setOnClickListener {
            listener?.onClickHomeDetailItem(item)
        }
        helper.setText(R.id.item_word, item.en)
        helper.setText(R.id.item_word_mean, item.zh)

    }


    var listener: OnDetailAdapterListener? = null

    fun setOnDetailAdapterListener(listener: OnDetailAdapterListener?) {
        this.listener = listener
    }

    interface OnDetailAdapterListener {

        fun onClickHomeDetailItem(item: ItemWord)
    }


}


