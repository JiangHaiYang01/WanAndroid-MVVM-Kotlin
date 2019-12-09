package com.allens.ui.activity

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.allens.config.Config
import com.allens.LogHelper
import com.allens.bean.login.LogInBean
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.model_http.XHttp
import com.allens.model_http.impl.OnBaseHttpListener
import com.allens.model_http.impl.OnHttpListener
import com.allens.tool.HttpTool
import com.allens.tools.R
import com.allens.tools.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterAct : BaseMVVMAct<ActivityRegisterBinding, RegisterModel, RegisterVM>() {

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {


        bind.etPwd.addTextChangedListener(RegisterTextWatcher(0, vm))

        bind.etPhone.addTextChangedListener(RegisterTextWatcher(1, vm))

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
            vm.register(vm.number.value, vm.pwd.value, object : OnBaseHttpListener<LogInBean> {
                override fun onSuccess(t: LogInBean) {
                    LogHelper.i("注册成功 $t")
                    if (t.errorCode == 0) {

                    } else {
                        Snackbar.make(bind.actRegisterParent, t.errorMsg, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(e: Throwable) {
                    LogHelper.i("注册失败 $e")
                    Snackbar.make(bind.actRegisterParent, "注册失败,请检查网络后重试~", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
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

class RegisterModel : BaseModel(), RegisterModelImpl {
    override fun register(
        number: String?,
        pwd: String?,
        listener: OnBaseHttpListener<LogInBean>
    ) {
        HttpTool.xHttp
            .doPost(
                lifecycle,
                LogInBean::class.java,
                "user/register",
                object : OnHttpListener<LogInBean>() {
                    override fun onMap(map: HashMap<String, Any>) {
                        super.onMap(map)
                        if (!number.isNullOrEmpty()) {
                            map["username"] = number
                        }
                        if (!pwd.isNullOrEmpty()) {
                            map["password"] = pwd
                            map["repassword"] = pwd
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

class RegisterVM : BaseVM<RegisterModel>(), RegisterModelImpl {

    //是否显示密码
    var isShowPwd: MutableLiveData<Boolean> = MutableLiveData()
    //是否可以点击
    var isClickLogin: MutableLiveData<Boolean> = MutableLiveData()
    //密码
    var pwd: MutableLiveData<String> = MutableLiveData()
    //账号
    var number: MutableLiveData<String> = MutableLiveData()

    override fun register(number: String?, pwd: String?, listener: OnBaseHttpListener<LogInBean>) {
        model.register(number, pwd, listener)
    }

}

interface RegisterModelImpl {
    fun register(
        number: String?,
        pwd: String?,
        listener: OnBaseHttpListener<LogInBean>
    )
}