package com.allens.adapter

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.databinding.BindingAdapter

//@BindingAdapter(
//    value = ["app:beforeTextChanged", "app:beforeTextChanged", "app:onTextChanged"],
//    requireAll = false
//)
//fun EditText.addTextChange() {
//    this.addTextChangedListener(object : TextWatcher {
//        override fun afterTextChanged(s: Editable?) {
//        }
//
//        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//        }
//
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//        }
//    })
//}

//密码是否可见
@BindingAdapter(
    value = ["app:etIsShowPwd"],
    requireAll = false
)
fun EditText.etIsShowPwd(isSHowPwd: Boolean) {
    if (isSHowPwd) {
        this.transformationMethod = HideReturnsTransformationMethod.getInstance()
    } else {
        this.transformationMethod = PasswordTransformationMethod.getInstance()
    }
    //让光标移动到最后
    setSelection(text.toString().trim().length)
}
