package s3.allens.impl

import java.util.ArrayList
import java.util.HashMap

interface GetFileListListener {
    fun onGetFileListStart()

    fun onGetFileListResult(list : ArrayList<HashMap<String, String>>?)
}