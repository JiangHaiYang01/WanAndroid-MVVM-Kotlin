package com.example.spark_word.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.lib_aws.R


//使用 kotlin  写adapter
class WordAdapter(
    data: MutableList<String?>
) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_word, data) {
    override fun convert(helper: BaseViewHolder?, item: String?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }
        helper.itemView.setOnClickListener {
            listener?.onClickHomeDetailItem(item)
        }
        helper.setText(R.id.item_word,item)

    }


    var listener: OnDetailAdapterListener? = null

    fun setOnDetailAdapterListener(listener: OnDetailAdapterListener?) {
        this.listener = listener
    }

    interface OnDetailAdapterListener {

        fun onClickHomeDetailItem(item: String)
    }


}


