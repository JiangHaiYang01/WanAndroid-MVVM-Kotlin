package com.allens.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.allens.Config
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.XHttp
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tools.R
import com.allens.tools.databinding.ActivityLoginBinding

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
            val get = vm.isShowPwd.value
            vm.isShowPwd.value = (get == false)
        }

        bind.loginBtnLogin.setOnClickListener {
            LogHelper.i("点击登录 账号 ${vm.number.value} 密码 ${vm.pwd.value}")
            vm.login(vm.number.value, vm.pwd.value, object :OnBaseHttpListener<String>{
                override fun onSuccess(t: String) {
                    LogHelper.i("登录请求成功 $t")
                }

                override fun onError(e: Throwable) {
                    LogHelper.i("登录请求失败 $e")
                }
            })
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
            vm.number.value = s.toString()
        } else {
            vm.pwd.value = s.toString()
        }

        if (vm.number.value.isNullOrEmpty() || vm.pwd.value.isNullOrEmpty()) {
            vm.isClickLogin.value = false
        } else {
            vm.isClickLogin.value = (true)
        }
    }

}


class LogInModel : BaseModel {
    fun login(
        number: String?,
        pwd: String?,
        listener: OnBaseHttpListener<String>
    ) {
        XHttp.Builder()
            .baseUrl(Config.baseURL)
            .retryOnConnectionFailure(false)
            .build()
            .doPost(String::class.java, "user/login", object : OnHttpListener<String>() {
                override fun onMap(map: HashMap<String, Any>) {
                    super.onMap(map)
                    if (!number.isNullOrEmpty()) {
                        map["username"] = number
                    }
                    if (!pwd.isNullOrEmpty()) {
                        map["password"] = pwd
                    }
                }

                override fun onSuccess(t: String) {
                    listener.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    listener.onError(e)
                }
            })
    }
}

class LogInVM : BaseVM<LogInModel>() {

    //是否显示密码
    var isShowPwd: MutableLiveData<Boolean> = MutableLiveData()
    //是否可以点击
    var isClickLogin: MutableLiveData<Boolean> = MutableLiveData()
    //密码
    var pwd: MutableLiveData<String> = MutableLiveData()
    //账号
    var number: MutableLiveData<String> = MutableLiveData()

    fun login(number: String?, pwd: String?, listener: OnBaseHttpListener<String>) {
        model.login(number, pwd, listener)
    }

}