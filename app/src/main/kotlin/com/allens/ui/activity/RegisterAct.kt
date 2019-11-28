package com.allens.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityRegisterBinding

class RegisterAct : BaseMVVMAct<ActivityRegisterBinding, RegisterModel, RegisterVM>() {
    override fun initMVVMListener() {
        bind.vm = vm



        bind.etPwd.addTextChangedListener(RegisterTextWatcher(0, vm))

        bind.etPhone.addTextChangedListener(RegisterTextWatcher(1, vm))

        bind.loginImgDelete.setOnClickListener {
            bind.etPhone.setText("")
        }

        bind.loginImgShow.setOnClickListener {
            val get = vm.isShowPwd.value
            vm.isShowPwd.value = (get == false)
        }
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_register
    }

    override fun createModel(): RegisterModel {
        return RegisterModel()
    }

    override fun createVMClass(): Class<RegisterVM> {
        return RegisterVM::class.java
    }

}


class RegisterTextWatcher(private val type: Int, private val vm: RegisterVM) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (type == 1) {
            vm.number.value = s.toString()
        } else {
            vm.pwd.value = s.toString()
        }

        if (vm.number.value.isNullOrEmpty() || vm.pwd.value.isNullOrEmpty()) {
            vm.isClickLogin.value = false
        } else {
            val value = vm.number.value
            if (value != null && value.length == 11) {
                vm.isClickLogin.value = (true)
            }
        }
    }

}

class RegisterModel : BaseModel

class RegisterVM : BaseVM<RegisterModel>() {

    //是否显示密码
    var isShowPwd: MutableLiveData<Boolean> = MutableLiveData()
    //是否可以点击
    var isClickLogin: MutableLiveData<Boolean> = MutableLiveData()
    //密码
    var pwd: MutableLiveData<String> = MutableLiveData()
    //账号
    var number: MutableLiveData<String> = MutableLiveData()
}