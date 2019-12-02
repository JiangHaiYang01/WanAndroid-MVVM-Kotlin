package com.allens.ui.adapter

import com.allens.data.dto.FgItemDto
import com.allens.tools.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


//使用 kotlin  写adapter
class MeFragmentItemAdapter(
    data: MutableList<FgItemDto>
) :
    BaseQuickAdapter<FgItemDto, BaseViewHolder>(R.layout.item_fg_me_btn, data) {
    override fun convert(helper: BaseViewHolder?, item: FgItemDto?) {

        if (helper == null) {
            return
        }
        if (item == null) {
            return
        }
        helper.setText(R.id.item_fg_me_tv, item.name)
            .setImageResource(R.id.item_fg_me_img, item.icon)
            .setVisible(R.id.item_fg_me_tv_right, item.isShowRight)


    }
}