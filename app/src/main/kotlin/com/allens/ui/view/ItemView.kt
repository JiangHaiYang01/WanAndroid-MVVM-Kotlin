package com.allens.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.allens.tools.R

//kotlin 中自定义view
class ItemView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val mContext: Context = context

    private var viewHolder: ItemView.ViewHolder

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.view_item_btn, this, true)
        viewHolder = ViewHolder(inflate)
    }


    class ViewHolder(inflate: View) {

    }


}