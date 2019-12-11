package com.allens.ui.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.allens.tools.R

class TitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val mContext: Context = context

    private var viewHolder: TitleView.ViewHolder

    init {
        val inflate = LayoutInflater.from(context).inflate(R.layout.include_title, this, true)
        viewHolder = ViewHolder(inflate)

    }


    class ViewHolder(inflate: View) {
        var back: ImageView = inflate.findViewById(R.id.include_img_back)
        var info: TextView = inflate.findViewById(R.id.include_tv_title)
    }


    fun setTitle(info: String) {
        viewHolder.info.text = info
    }

    fun setBack(activity: Activity) {
        viewHolder.back.setOnClickListener {
            activity.finish()
        }
    }

    fun setBack(fragment: Fragment) {
        viewHolder.back.setOnClickListener {
            fragment.activity?.finish()
        }
    }



}