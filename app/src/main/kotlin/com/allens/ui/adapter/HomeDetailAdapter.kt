package com.allens.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.allens.bean.HomeDetailResultDataBean
import com.allens.tools.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


//使用 kotlin  写adapter
class HomeDetailAdapter(
    data: MutableList<HomeDetailResultDataBean>?
) :
    BaseQuickAdapter<HomeDetailResultDataBean, BaseViewHolder>(R.layout.item_home_detail, data) {
    override fun convert(helper: BaseViewHolder?, item: HomeDetailResultDataBean?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }

        if (item.author.isEmpty()) {
            helper.setText(R.id.item_home_detail_tv_user_name, "未知")
        } else {
            helper.setText(R.id.item_home_detail_tv_user_name, item.author)
        }
        helper.setText(R.id.item_home_detail_tv_user_title, item.title)
        helper.setText(R.id.item_home_detail_tv_time, item.niceDate)
        helper.itemView.setOnClickListener {
            listener?.onClickHomeDetailItem(item)
        }

        helper.getView<ImageView>(R.id.item_home_detail_img_user_heard).setOnClickListener {
            listener?.onClickHomeDetailAuthor(item)
        }

        helper.getView<TextView>(R.id.item_home_detail_tv_user_name).setOnClickListener {
            listener?.onClickHomeDetailAuthor(item)
        }
    }


    var listener: OnHomeDetailAdapterListener? = null

    fun setOnHomeDetailAdapterListener(listener: OnHomeDetailAdapterListener?) {
        this.listener = listener
    }
}


interface OnHomeDetailAdapterListener {
    fun onClickHomeDetailAuthor(item: HomeDetailResultDataBean)

    fun onClickHomeDetailItem(item: HomeDetailResultDataBean)
}

