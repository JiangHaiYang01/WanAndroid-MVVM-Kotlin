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
}
