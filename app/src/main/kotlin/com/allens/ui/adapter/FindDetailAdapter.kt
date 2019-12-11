package com.allens.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.allens.bean.home_detail.DataX
import com.allens.tools.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


class FindDetailAdapter(
    data: MutableList<DataX>?
) :
    BaseQuickAdapter<DataX, BaseViewHolder>(R.layout.item_find_detail, data) {
    override fun convert(helper: BaseViewHolder?, item: DataX?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }
        if (item.shareUser.isEmpty()) {
            helper.setText(R.id.item_home_detail_tv_user_name, "未知")
        } else {
            helper.setText(R.id.item_home_detail_tv_user_name, item.shareUser)
        }
        helper.setText(R.id.item_home_detail_tv_user_title, item.title)
            .setText(R.id.item_home_detail_tv_time, item.niceDate)
            .setText(
                R.id.item_home_detail_tv_tab,
                item.superChapterName + " / " + item.chapterName
            )
            .setVisible(R.id.item_find_detail_img_new,item.fresh)


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

    interface OnHomeDetailAdapterListener {
        fun onClickHomeDetailAuthor(item: DataX)

        fun onClickHomeDetailItem(item: DataX)
    }


}


