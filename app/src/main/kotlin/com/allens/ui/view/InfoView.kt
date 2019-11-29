package com.allens.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.allens.tools.R

class InfoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val mContext: Context = context

    private var viewHolder: InfoView.ViewHolder

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.view_info, this, true)
        viewHolder = ViewHolder(inflate)
    }


    class ViewHolder(inflate: View) {
        var value: TextView = inflate.findViewById<TextView>(R.id.view_info_value)
        var info: TextView = inflate.findViewById<TextView>(R.id.view_info_info)
    }


    fun setInfo(info: String) {
        viewHolder.info.text = info
    }


    fun setData(data: String) {
        viewHolder.value.text = data
    }


}