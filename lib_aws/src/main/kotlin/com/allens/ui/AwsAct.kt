package com.allens.ui

import android.os.Environment
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.allens.LogHelper
import com.allens.model_base.base.impl.BaseMVVMAct
import com.allens.model_base.base.impl.BaseModel
import com.allens.model_base.base.impl.BaseVM
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.example.lib_aws.R
import com.example.lib_aws.databinding.ActivityAwsBinding
import com.example.spark_word.adapter.WordAdapter
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.Permission
import s3.allens.AwsS3Tools
import s3.allens.impl.GetFileListListener
import s3.allens.impl.OnAwsDownLoadListener
import java.util.*


/**

 * @Author allens
 * @Date 2019-12-17-10:48
 * @Email 18856907654@163.com
 */
@Route(path = "/aws/act")
class AwsAct : BaseMVVMAct<ActivityAwsBinding, AwsModel, AwsVm>(), GetFileListListener,
    WordAdapter.OnDetailAdapterListener, OnAwsDownLoadListener {


    var tools: AwsS3Tools? = null

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

        //title
        bind.actAwsTitle.findViewById<TextView>(R.id.include_tv_title).text = "AWS"


        //adapter
        bind.actAwsRy.adapter = vm.adapter
        vm.adapter.setOnDetailAdapterListener(this)

        //get list
        tools = AwsS3Tools(this)
        tools?.getFileList(this, "wordStory")
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

    override fun onClickHomeDetailItem(item: String) {
        LogHelper.i("点击了 item $item")

        AndPermission.with(this)
            .runtime()
            .permission(Permission.Group.STORAGE)
            .onGranted{
//                tools?.downLoad(FileHelper.getBasePath(), item, this)
                tools?.downLoad(Environment.getExternalStorageDirectory().toString() + "/" + item, item, this)
            }
            .onDenied{

            }
            .start()


    }

    override fun onAwsDownloadFailed(throwable: Throwable?) {
        LogHelper.i("下载失败 ${throwable?.message}")
    }

    override fun onAwsDownloadComplete() {
        LogHelper.i("下载finish")


    }
    override fun onAwsDownLoadStatusChange(status: TransferState?) {
        LogHelper.i("下载状态改变 $status")
    }

    override fun onAwsDownLoadProgress(id: Int, bytesCurrent: Long, bytesTotal: Long) {
        LogHelper.i("下载进度改变  id: $id   bytesCurrent: $bytesCurrent  bytesTotal: $bytesTotal")
    }
}


class AwsModel : BaseModel() {}


class AwsVm : BaseVM<AwsModel>() {


    val data = mutableListOf<String?>()

    var adapter = WordAdapter(data)
}