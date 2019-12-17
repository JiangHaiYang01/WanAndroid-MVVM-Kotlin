package com.allens.ui

import com.alibaba.android.arouter.facade.annotation.Route
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.example.lib_aws.R
import com.example.lib_aws.databinding.ActivityAwsBinding
import com.example.spark_word.adapter.WordAdapter
import s3.allens.AwsS3Tools
import s3.allens.impl.GetFileListListener
import java.util.ArrayList
import java.util.HashMap

/**

 * @Author allens
 * @Date 2019-12-17-10:48
 * @Email 18856907654@163.com
 */
@Route(path = "/aws/act")
class AwsAct : BaseMVVMAct<ActivityAwsBinding, AwsModel, AwsVm>(), GetFileListListener {
    override fun createModel(): AwsModel {
        return AwsModel()
    }

    override fun createVMClass(): Class<AwsVm> {
        return AwsVm::class.java
    }

    override fun getContentViewId(): Int {
        return R.layout.activity_aws
    }

    override fun initMVVMBind() {
        bind.vm = vm
    }

    override fun initMVVMListener() {

        bind.actAwsRy.adapter = vm.adapter
        val tools = AwsS3Tools(this)
        tools.getFileList(this, "wordStory")
    }

    override fun onGetFileListStart() {
        LogHelper.i("开始获取 aws 列表")
    }

    override fun onGetFileListResult(list: ArrayList<HashMap<String, String>>?) {
        LogHelper.i("aws 列表 $list")
        list?.forEach {
            vm.data.add(it["key"])
        }
        vm.adapter.notifyDataSetChanged()
    }
}


class AwsModel : BaseModel() {}


class AwsVm : BaseVM<AwsModel>() {


    val data = mutableListOf<String?>()

    var adapter = WordAdapter(data)
}