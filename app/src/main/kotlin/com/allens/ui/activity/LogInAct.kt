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


        bind.etPwd.addTextChangedListener(MyTextWatcher(0, vm))

        bind.etPhone.addTextChangedListener(MyTextWatcher(1, vm))

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

        bind.loginBtnLogin.setOnClickListener {
            LogHelper.i("点击登录 账号 ${vm.number.get()} 密码 ${vm.pwd.get()}")
        }
    }
}

class MyTextWatcher(private val type: Int, private val vm: LogInVM) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (type == 1) {
            vm.number.set(s.toString())
        } else {
            vm.pwd.set(s.toString())
        }

        if (vm.number.get().isNullOrEmpty() || vm.pwd.get().isNullOrEmpty()) {
            vm.isClickLogin.set(false)
        } else {
            vm.isClickLogin.set(true)
        }
    }

}


class LogInModel : BaseModel

class LogInVM : BaseVM<LogInModel>() {

    //是否显示密码
    var isShowPwd: ObservableField<Boolean> = ObservableField()
    //是否可以点击
    var isClickLogin: ObservableField<Boolean> = ObservableField()
    //密码
    var pwd: ObservableField<String> = ObservableField()
    //账号
    var number: ObservableField<String> = ObservableField()


}