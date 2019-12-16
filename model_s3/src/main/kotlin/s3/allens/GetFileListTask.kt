package s3.allens

import android.os.AsyncTask
import com.amazonaws.services.s3.AmazonS3Client
import s3.allens.impl.GetFileListListener
import java.util.*

class GetFileListTask constructor(
    private val s3Client: AmazonS3Client,
    private val bucket: String,
    private val prefix: String,
    private val listener: GetFileListListener
) : AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {


    override fun onPreExecute() {
        super.onPreExecute()
        listener.onGetFileListStart()
    }

    override fun doInBackground(vararg params: Void?): ArrayList<HashMap<String, String>> {
        val list = ArrayList<HashMap<String, String>>()
        val objectSummaries = s3Client.listObjects(bucket, prefix).objectSummaries
        for (summary in objectSummaries) {
            val map = HashMap<String, String>()
            map["key"] = summary.key
            list.add(map)
        }
        return list
    }


    override fun onPostExecute(result: ArrayList<HashMap<String, String>>?) {
        super.onPostExecute(result)
        listener.onGetFileListResult(result)
    }
}