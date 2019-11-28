package com.allens.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityLoginBinding
import java.util.logging.Logger

class LogInAct : BaseMVVMAct<ActivityLoginBinding, LogInModel, LogInVM>() {

    override fun getContentViewId(): Int {
        return R.layout.activity_login
    }

    override fun createModel(): LogInModel {
        return LogInModel()
    }

    override fun createVMClass(): Class<LogInVM> {
        return LogInVM::class.java
    }

    override fun initMVVMListener() {
        bind.vm = vm


        bind.etPwd.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.pwd.set(s.toString())
            }
        })

        bind.etPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                vm.number.set(s.toString())
            }
        })

        bind.loginImgDelete.setOnClickListener {
            bind.etPhone.setText("")
        }

        bind.loginImgShow.setOnClickListener {
            val get = vm.isShowPwd.get()
            if (get == false) {
                vm.isShowPwd.set(true)
            } else {
                vm.isShowPwd.set(false)
            }
        }
    }
}


class LogInModel : BaseModel

class LogInVM : BaseVM<LogInModel>() {

    //是否显示密码
    var isShowPwd: ObservableField<Boolean> = ObservableField()
    var pwd: ObservableField<String> = ObservableField()
    var number: ObservableField<String> = ObservableField()


}