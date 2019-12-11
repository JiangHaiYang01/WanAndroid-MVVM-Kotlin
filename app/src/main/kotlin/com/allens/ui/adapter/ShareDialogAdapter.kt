package com.allens.ui.adapter

import com.allens.bean.home_detail.DataX
import com.allens.tools.R
import com.allens.ui.dialog.ShareDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

class ShareDialogAdapter(
    data: MutableList<ShareDialog.ShareDialogItem>?,
    private val listener: OnItemClickListener
) :
    BaseQuickAdapter<ShareDialog.ShareDialogItem, BaseViewHolder>(
        R.layout.view_dialog_share,
        data
    ) {
    override fun convert(helper: BaseViewHolder?, item: ShareDialog.ShareDialogItem?) {

        if (helper == null) {
            return
        }

        if (item == null) {
            return
        }

        helper.setImageResource(R.id.dialog_share_img, item.id)
            .setText(R.id.dialog_share_name, item.name)


        helper.itemView.setOnClickListener {
            listener.onItem(item)
        }
    }


    interface OnItemClickListener {
        fun onItem(item: ShareDialog.ShareDialogItem)
    }

}