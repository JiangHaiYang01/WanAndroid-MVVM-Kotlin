package com.allens.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.allens.bean.author_detail.DataX
import com.allens.tools.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


//使用 kotlin  写adapter
class MeDetailAdapter(
    data: MutableList<String>?
) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_official_detail, data) {
    override fun convert(helper: BaseViewHolder?, item: String?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }

    }


    var listener: OnDetailAdapterListener? = null

    fun setOnDetailAdapterListener(listener: OnDetailAdapterListener?) {
        this.listener = listener
    }

    interface OnDetailAdapterListener {

        fun onClickHomeDetailItem(item: DataX)
    }


}


