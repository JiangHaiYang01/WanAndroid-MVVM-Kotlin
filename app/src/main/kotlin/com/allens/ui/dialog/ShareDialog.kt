package com.allens.ui.dialog

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.allens.LogHelper
import com.allens.tools.R
import com.allens.ui.adapter.ShareDialogAdapter
import com.google.android.material.tabs.TabLayout
import dialog.BottomDialog

class ShareDialog(context: Context, private val listener: OnShareDialogListener) :
    BottomDialog(context),
    ShareDialogAdapter.OnItemClickListener {
    override fun getLayoutId(): Int {
        return R.layout.dialog_share
    }

    override fun getDialogWidth(): Double {
        return 1.0
    }

    override fun getInflateHeightFull(): Boolean {
        return false
    }

    override fun onLayoutView(view: View) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.dialog_ry)
        recyclerView.adapter = ShareDialogAdapter(
            mutableListOf(
                ShareDialogItem("微信", R.drawable.dialog_share_wechat, 1),
                ShareDialogItem("朋友圈", R.drawable.dialog_share_friend, 2),
                ShareDialogItem("QQ", R.drawable.dialog_share_qq, 3),
                ShareDialogItem("微博", R.drawable.dialog_share_weibo, 4),
                ShareDialogItem("其他", R.drawable.dialog_share_other, 8),
                ShareDialogItem("收藏", R.drawable.dialog_share_star, 5),
                ShareDialogItem("复制链接", R.drawable.dialog_share_copy, 6),
                ShareDialogItem("游览器打开", R.drawable.dialog_share_qq, 7)

            ),
            this
        )


        view.findViewById<TextView>(R.id.dialog_share_cancel).setOnClickListener {
            dismiss()
        }
    }

    data class ShareDialogItem(val name: String, val id: Int, val tag: Int)

    override fun onItem(item: ShareDialogItem) {
        dismiss()
        when (item.tag) {
            1 -> {

            }
            2 -> {

            }
            3 -> {

            }
            4 -> {

            }
            5 -> {
                listener.onCollection()
            }
            6 -> {

            }
            7 -> {

            }
            8 -> {

            }

        }
    }


    interface OnShareDialogListener {
        //收藏
        fun onCollection()
    }
}