package com.allens.adapter

import android.graphics.drawable.Drawable
import android.widget.RadioButton
import androidx.databinding.BindingAdapter

@BindingAdapter(
    value = ["app:radioButtonTopSize",
        "app:radioButtonBoundsLeft",
        "app:radioButtonBoundsTop",
        "app:radioButtonBoundsRight",
        "app:radioButtonBoundsBottom"
    ], requireAll = false
)
fun RadioButton.changeTopImageSize(
    drawable: Drawable?,
    left: Int?,
    top: Int?,
    right: Int?,
    bottom: Int?
) {

    if (drawable != null) {
        //第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        drawable.setBounds(left ?: 0, top ?: 0, right ?: 0, bottom ?: 0)
        //只放上面
        setCompoundDrawables(null, drawable, null, null)
    }
}

@BindingAdapter(
    value = ["app:radioButtonLeftSize",
        "app:radioButtonBoundsLeft",
        "app:radioButtonBoundsTop",
        "app:radioButtonBoundsRight",
        "app:radioButtonBoundsBottom"], requireAll = false
)
fun RadioButton.changeLeftImageSize(
    drawable: Drawable?,
    left: Int?,
    top: Int?,
    right: Int?,
    bottom: Int?
) {

    if (drawable != null) {
        //第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        drawable.setBounds(left ?: 0, top ?: 0, right ?: 0, bottom ?: 0)
        //只放上面
        setCompoundDrawables(drawable, null, null, null)
    }
}


@BindingAdapter(
    value = ["app:radioButtonRightSize",
        "app:radioButtonBoundsLeft",
        "app:radioButtonBoundsTop",
        "app:radioButtonBoundsRight",
        "app:radioButtonBoundsBottom"], requireAll = false
)
fun RadioButton.changeRightImageSize(
    drawable: Drawable?,
    left: Int?,
    top: Int?,
    right: Int?,
    bottom: Int?
) {

    if (drawable != null) {
        //第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        drawable.setBounds(left ?: 0, top ?: 0, right ?: 0, bottom ?: 0)
        //只放上面
        setCompoundDrawables(null, null, drawable, null)
    }
}

@BindingAdapter(
    value = ["app:radioButtonBottomSize",
        "app:radioButtonBoundsLeft",
        "app:radioButtonBoundsTop",
        "app:radioButtonBoundsRight",
        "app:radioButtonBoundsBottom"], requireAll = false
)
fun RadioButton.changeBottomImageSize(
    drawable: Drawable?,
    left: Int?,
    top: Int?,
    right: Int?,
    bottom: Int?
) {

    if (drawable != null) {
        //第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        drawable.setBounds(left ?: 0, top ?: 0, right ?: 0, bottom ?: 0)
        //只放上面
        setCompoundDrawables(null, null, drawable, null)
    }
}