package com.allens.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.allens.bean.project_detail.DataX
import com.allens.tools.R
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


//使用 kotlin  写adapter
class ProjectDetailAdapter(
    data: MutableList<DataX>?
) :
    BaseQuickAdapter<DataX, BaseViewHolder>(R.layout.item_project_detail, data) {
    override fun convert(helper: BaseViewHolder?, item: DataX?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }
        helper.setText(R.id.item_home_detail_tv_user_title, item.title)
        helper.setText(R.id.item_home_detail_tv_user_info, item.desc)

        helper.setText(R.id.item_home_detail_tv_time, item.niceDate)
        helper.itemView.setOnClickListener {
            listener?.onClickHomeDetailItem(item)
        }
        Glide.with(mContext).load(item.envelopePic).into(helper.getView(R.id.item_home_detail_img))

    }


    var listener: OnDetailAdapterListener? = null

    fun setOnDetailAdapterListener(listener: OnDetailAdapterListener?) {
        this.listener = listener
    }

    interface OnDetailAdapterListener {

        fun onClickHomeDetailItem(item: DataX)
    }


}


