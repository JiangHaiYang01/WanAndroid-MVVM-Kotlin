package com.allens.ui.dialog

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.allens.tools.R
import com.google.android.material.tabs.TabLayout
import dialog.BottomDialog

class ShareDialog(context: Context) : BottomDialog(context) {
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
        topLayout(view)
        bottomLayout(view)
    }

    private fun topLayout(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.dialog_tl_top)
        val list = mutableListOf(
            ShareDialogItem("微信", R.drawable.dialog_share_wechat),
            ShareDialogItem("朋友圈", R.drawable.dialog_share_friend),
            ShareDialogItem("QQ", R.drawable.dialog_share_qq),
            ShareDialogItem("微博", R.drawable.dialog_share_weibo),
            ShareDialogItem("其他", R.drawable.dialog_share_other)
        )
        list.forEach {
            val tab = tabLayout.newTab()
            //添加自定义的布局
            tab.setCustomView(R.layout.view_dialog_share)
            val textView = tab.customView?.findViewById<TextView>(R.id.dialog_share_name)
            textView?.text = it.name

            val imageView = tab.customView?.findViewById<ImageView>(R.id.dialog_share_img)
            imageView?.setImageResource(it.id)
            tabLayout.addTab(tab)
        }
    }

    private fun bottomLayout(view: View) {
        val tabLayout = view.findViewById<TabLayout>(R.id.dialog_tl_bottom)
        val list = mutableListOf(
            ShareDialogItem("微信", R.drawable.dialog_share_wechat),
            ShareDialogItem("朋友圈", R.drawable.dialog_share_friend),
            ShareDialogItem("QQ", R.drawable.dialog_share_qq),
            ShareDialogItem("微博", R.drawable.dialog_share_weibo),
            ShareDialogItem("其他", R.drawable.dialog_share_other)
        )
        list.forEach {
            val tab = tabLayout.newTab()
            //添加自定义的布局
            tab.setCustomView(R.layout.view_dialog_share)
            val textView = tab.customView?.findViewById<TextView>(R.id.dialog_share_name)
            textView?.text = it.name

            val imageView = tab.customView?.findViewById<ImageView>(R.id.dialog_share_img)
            imageView?.setImageResource(it.id)
            tabLayout.addTab(tab)
        }
    }

    data class ShareDialogItem(val name: String, val id: Int)
}