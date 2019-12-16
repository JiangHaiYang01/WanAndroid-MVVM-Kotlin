package s3.allens

import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import s3.allens.impl.GetFileListListener

class AwsS3Tools constructor(context: Context) {


    var util: Util = Util()
    var transferUtility: TransferUtility = util.getTransferUtility(context)
    var s3: AmazonS3Client = util.getS3Client(context)
    val bucket = AWSConfiguration(context).optJsonObject("S3TransferUtility").optString("Bucket")
    var fileListTask: GetFileListTask? = null


    //获取文件夹列表
    fun getFileList(listener: GetFileListListener, prefix: String) {
        fileListTask = GetFileListTask(s3, bucket, prefix, listener)
        fileListTask?.execute()
    }

    //取消任务
    fun cancelGetFileListTask() {
        fileListTask?.cancel(true)
    }
}