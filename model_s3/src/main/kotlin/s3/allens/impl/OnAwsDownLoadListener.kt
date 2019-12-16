package s3.allens.impl

import com.amazonaws.mobileconnectors.s3.transferutility.TransferState

/**

 * @Author allens
 * @Date 2019-12-16-19:45
 * @Email 18856907654@163.com
 */
interface OnAwsDownLoadListener {
    fun onAwsDownloadFailed(throwable: Throwable?)

    fun onAwsDownloadComplete()

    fun onAwsDownLoadStatusChange(status: TransferState?)

    fun onAwsDownLoadProgress(id: Int, bytesCurrent: Long, bytesTotal: Long)
}