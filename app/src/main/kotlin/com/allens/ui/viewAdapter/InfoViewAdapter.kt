package com.allens.ui.viewAdapter

import androidx.databinding.BindingAdapter
import com.allens.ui.view.InfoView

@BindingAdapter(value = ["custom:infoViewSetData"])
fun InfoView.setData(data: String) {
    setData(data)
}