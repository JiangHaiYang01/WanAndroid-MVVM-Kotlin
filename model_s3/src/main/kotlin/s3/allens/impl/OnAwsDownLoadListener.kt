package s3.allens.impl

import com.amazonaws.mobileconnectors.s3.transferutility.TransferState

/**

 * @Author allens
 * @Date 2019-12-16-19:45
 * @Email 18856907654@163.com
 */
interface OnAwsDownLoadListener {
    fun onAwsDownloadFailed(key: String, throwable: Throwable?)

    fun onAwsDownloadComplete(key: String, path: String)

    fun onAwsDownLoadStatusChange(
        key: String,
        status: TransferState?
    )

    fun onAwsDownLoadProgress(key: String,id: Int, bytesCurrent: Long, bytesTotal: Long)
}