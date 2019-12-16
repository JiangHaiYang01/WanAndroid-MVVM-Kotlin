package s3.allens

import android.content.Context
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferType
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import s3.allens.impl.GetFileListListener
import s3.allens.impl.OnAwsDownLoadListener
import java.io.File
import java.lang.Exception

class AwsS3Tools constructor(context: Context) {


    //
    var util: Util = Util()
    var transferUtility: TransferUtility = util.getTransferUtility(context)
    var s3: AmazonS3Client = util.getS3Client(context)
    //同名称
    val bucket = AWSConfiguration(context).optJsonObject("S3TransferUtility").optString("Bucket")
    //获取列表
    var fileListTask: GetFileListTask? = null
    //A List of all transfers
//    var observers = mutableListOf<TransferObserver>()


    //获取文件夹列表
    fun getFileList(listener: GetFileListListener, prefix: String) {
        fileListTask = GetFileListTask(s3, bucket, prefix, listener)
        fileListTask?.execute()
    }

    //取消任务
    fun cancelGetFileListTask() {
        fileListTask?.cancel(true)
    }


    //下载任务
    fun downLoad(path: String, key: String, listener: OnAwsDownLoadListener) {
        val download = transferUtility.download(key, File(path))
        download.setTransferListener(object : TransferListener {
            override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                listener.onAwsDownLoadProgress(id, bytesCurrent, bytesTotal)
            }

            override fun onStateChanged(p0: Int, p1: TransferState?) {
                when (p1) {
                    TransferState.COMPLETED -> {
                        listener.onAwsDownloadComplete()
                    }
                }
                listener.onAwsDownLoadStatusChange(p1)
            }

            override fun onError(p0: Int, p1: Exception?) {
                listener.onAwsDownloadFailed(p1)
            }
        })
    }

    //取消下载
    fun cancelDownLoad() {
        transferUtility.cancelAllWithType(TransferType.DOWNLOAD)
    }

}