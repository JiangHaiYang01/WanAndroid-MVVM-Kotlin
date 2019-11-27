package com.allens.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.allens.tools.R
import com.allens.tools.databinding.ActivityMainBinding

class MainActivity : BaseMVVMAct<ActivityMainBinding, MainModel, MainVM>() {
    override fun createModel(): MainModel {

        return MainModel()
    }

    override fun createVMClass(): Class<MainVM> {
        return MainVM::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun initMVVMListener() {
        bind.vm = MainVM()
    }

}

class MainModel : BaseModel

class MainVM : BaseVM<MainModel>() {

    var imgUrl: String? = "http://pic1.win4000.com/pic/a/0b/333747d77d_250_300.jpg"
    var img = R.mipmap.act_splash_def
}
