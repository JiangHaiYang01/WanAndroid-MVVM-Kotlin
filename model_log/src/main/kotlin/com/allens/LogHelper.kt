package com.allens

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import com.allens.LogHelper.context
import com.orhanobut.logger.*
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-13 09:46
 * @Version:        1.0
 */
object LogHelper {


    lateinit var context: Context
    private var isDebug: Boolean = true

    fun init(
        context: Context,
        path: String,
        maxLogRom: Int,
        maxLogFileSize: Int,
        isDebug: Boolean
    ) {
        this.context = context
        this.isDebug = isDebug
        Logger.addLogAdapter(AndroidLogAdapter())
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        val ht = HandlerThread("log.$path")
        ht.start()

        val handler: Handler =
            WriteHandler(ht.looper, path, maxLogRom * 1024 * 1000, maxLogFileSize)
        val diskLogStrategy =
            DiskLogStrategy(handler)

        val csvFormatStrategy: FormatStrategy =
            CsvFormatStrategy.newBuilder()
                .tag("log")
                .logStrategy(diskLogStrategy)
                .build()
        Logger.addLogAdapter(
            DiskLogAdapter(
                csvFormatStrategy
            )
        )
    }


    fun i(message: String, vararg args: Any?) {
        if (isDebug)
            Logger.i(message, args)
    }


    fun log(
        priority: Int,
        tag: String?,
        message: String?,
        throwable: Throwable?
    ) {
        if (isDebug)
            Logger.log(priority, tag, message, throwable)
    }

    fun d(message: String, vararg args: Any?) {
        if (isDebug) {
            Logger.d(message, *args)
        }
    }

    fun d(`object`: Any?) {
        if (isDebug) {
            Logger.d(`object`)
        }
    }


    fun e(message: String, vararg args: Any?) {
        if (isDebug) {
            Logger.e(message, *args)
        }
    }


    fun e(
        throwable: Throwable?,
        message: String,
        vararg args: Any?
    ) {
        if (isDebug) {
            Logger.e(throwable, message, *args)
        }
    }


    fun v(message: String, vararg args: Any?) {
        if (isDebug) {
            Logger.v(message, *args)
        }
    }

    fun w(message: String, vararg args: Any?) {
        if (isDebug) {
            Logger.w(message, *args)
        }
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    fun wtf(message: String, vararg args: Any?) {
        if (isDebug) {
            Logger.wtf(message, *args)
        }
    }

    /**
     * Formats the given json content and print it
     */
    fun json(json: String?) {
        if (isDebug) {
            Logger.json(json)
        }
    }

    /**
     * Formats the given xml content and print it
     */
    fun xml(xml: String?) {
        if (isDebug) {
            Logger.xml(xml)
        }
    }
}


class WriteHandler(
    looper: Looper,
    private val path: String,
    private val maxLogRom: Int,
    private val maxLogFileSize: Int
) : Handler(looper) {

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val content = msg.obj as String
        var fileWriter: FileWriter? = null
        val logFile: File = getLogFile(path, maxLogRom, maxLogFileSize)
        try {
            fileWriter = FileWriter(logFile, true)
            writeLog(fileWriter, content)
            fileWriter.flush()
            fileWriter.close()
        } catch (e: IOException) {
            if (fileWriter != null) {
                try {
                    fileWriter.flush()
                    fileWriter.close()
                } catch (e1: IOException) { /* fail silently */
                }
            }
        }
    }

}

fun getSimpleClassName(name: String): String {
    val lastIndex = name.lastIndexOf(".")
    return name.substring(lastIndex + 1)
}

private fun getLogFile(path: String, maxLogRom: Int, maxLogFileSize: Int): File {
    val folder = File(path)
    if (!folder.exists()) {
        folder.mkdirs()
    }

    var newFileCount: Int = ShareLogUtil(context).getInt("log_index", 1)

    var newFile: File
    var existingFile: File? = null
    newFile = File(folder, String.format("%s_%s.csv", "log", newFileCount))


    while (newFile.exists()) {
        existingFile = newFile
        newFileCount++
        newFile =
            File(folder, String.format("%s_%s.csv", "log", newFileCount))
    }


    if (existingFile != null) {
        if (existingFile.length() >= maxLogRom) {
            ShareLogUtil(context).putInt("log_index", newFileCount)
            delete(path, maxLogFileSize)
            return newFile
        }
        return existingFile
    }

    return newFile
}

fun delete(path: String, maxLogFileSize: Int) {
    //读取文件 如果文件是空 return
    val files: Array<File> = getFiles(path) ?: return
    if (files.size > maxLogFileSize - 1) {
        getLastFile(files)?.delete()
    }
}

fun getLastFile(files: Array<File>): File? {
    var minTime = 0L
    var path: String? = null
    for (i in files.indices) {
        val time = files[i].lastModified()
        if (i == 0) {
            path = files[i].absolutePath
            minTime = time
        }
        if (minTime > time) {
            minTime = time
            path = files[i].absolutePath
        }
    }
    if (path == null) {
        return null
    }
    return File(path)
}


fun getFiles(path: String): Array<File>? {
    val file = File(path)
    return file.listFiles()
}

fun writeLog(fileWriter: FileWriter, content: String) {
    print("[log>>>>] $content")
    fileWriter.append(content)
}
