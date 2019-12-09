package com.allens.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.allens.config.Config
import com.allens.LogHelper
import com.allens.bean.LogInBean
import com.allens.config.SpConfig
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.XHttp
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.status.UserStatus
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.tencent.mmkv.MMKV

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


        bind.etPwd.addTextChangedListener(LoginTextWatcher(0, vm))

        bind.etPhone.addTextChangedListener(LoginTextWatcher(1, vm))


        bind.etPhone.setText("18856907654")
        bind.etPwd.setText("470988")

        bind.loginImgDelete.setOnClickListener {
            bind.etPhone.setText("")
        }

        bind.loginImgShow.setOnClickListener {
            val get = vm.isShowPwd.value
            vm.isShowPwd.value = (get == false)
        }

        bind.loginBtnLogin.setOnClickListener {
            //隐藏软键盘
            hideSoftInput()
            LogHelper.i("点击登录 账号 ${vm.number.value} 密码 ${vm.pwd.value}")
            vm.login(vm.number.value, vm.pwd.value, object : OnBaseHttpListener<LogInBean> {
                override fun onSuccess(t: LogInBean) {
                    LogHelper.i("登录请求成功 ")
                    if (t.errorCode == 0) {
                        UserStatus.login(t)
                        //退出当前界面
                        finish()

                    } else {
                        Snackbar.make(bind.actLoginParent, t.errorMsg, Snackbar.LENGTH_SHORT).show()
                    }
                }

                override fun onError(e: Throwable) {
                    LogHelper.i("登录请求失败 $e")
                    Snackbar.make(bind.actLoginParent, "登录失败,请检查网络后重试~", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
        }


        bind.registerNewCount.setOnClickListener {
            LogHelper.i("点击注册新用户")
            startActivity(RegisterAct::class.java)
        }
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }
}

class LoginTextWatcher(private val type: Int, private val vm: LogInVM) : TextWatcher {
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


class LogInModel : BaseModel(), LogInModelImpl {
    override fun login(
        number: String?,
        pwd: String?,
        listener: OnBaseHttpListener<LogInBean>
    ) {
        HttpTool.xHttp
            .doPost(
                lifecycle,
                LogInBean::class.java,
                "user/login",
                object : OnHttpListener<LogInBean>() {
                    override fun onMap(map: HashMap<String, Any>) {
                        super.onMap(map)
                        if (!number.isNullOrEmpty()) {
                            map["username"] = number
                        }
                        if (!pwd.isNullOrEmpty()) {
                            map["password"] = pwd
                        }
                    }

                    override fun onSuccess(t: LogInBean) {
                        listener.onSuccess(t)
                    }

                    override fun onError(e: Throwable) {
                        listener.onError(e)
                    }
                })
    }
}

class LogInVM : BaseVM<LogInModel>(), LogInModelImpl {


    //是否显示密码
    var isShowPwd: MutableLiveData<Boolean> = MutableLiveData()
    //是否可以点击
    var isClickLogin: MutableLiveData<Boolean> = MutableLiveData()
    //密码
    var pwd: MutableLiveData<String> = MutableLiveData()
    //账号
    var number: MutableLiveData<String> = MutableLiveData()

    override fun login(number: String?, pwd: String?, listener: OnBaseHttpListener<LogInBean>) {
        model.login(number, pwd, listener)
    }

}


interface LogInModelImpl {
    fun login(
        number: String?,
        pwd: String?,
        listener: OnBaseHttpListener<LogInBean>
    )
}