package com.allens.model_base.tools

import android.R.attr
import android.os.Environment
import android.os.StatFs
import android.util.Log
import com.allens.model_base.base.BaseApplication
import java.io.*
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow


/**
 *
 * @Description:
 * @Author:         Allens
 * @CreateDate:     2019-11-13 11:03
 * @Version:        1.0
 */
//获取更路径
fun getBasePath(): String {
    var p: String = Environment.getExternalStorageDirectory().path
    val f: File? = BaseApplication.instance.getExternalFilesDir(null)
    if (null != f) {
        p = f.absolutePath
    }
    return p
}

//检查SDCard存在并且可以读写
fun isSDCardState(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}


/**
 * 判断文件是否已经存在
 * @param fileName 要检查的文件名
 * @return boolean, true表示存在，false表示不存在
 */
fun isFileExist(fileName: String): Boolean {
    val file = File(fileName)
    return file.exists()
}


/**
 * 新建目录
 * @param path 目录的绝对路径
 * @return 创建成功则返回true
 */
fun createFolder(path: String): Boolean {
    val file = File(path)
    return file.mkdir()
}

/**
 * 创建文件
 * @param path 文件所在目录的目录名
 * @param fileName 文件名
 * @return 文件新建成功则返回true
 */
fun createFile(path: String, fileName: String): Boolean {
    val file = File(path + File.separator + fileName)
    return if (file.exists()) {
        false
    } else {
        try {
            file.createNewFile()
        } catch (e: IOException) {
            false
        }
    }
}


/**
 * 删除单个文件
 * @param path 文件所在的绝对路径
 * @param fileName 文件名
 * @return 删除成功则返回true
 */
fun deleteFile(path: String, fileName: String): Boolean {
    val file = File(path + File.separator + fileName)
    return file.exists() && file.delete()
}


/**
 * 删除一个目录（可以是非空目录）
 * @param dir 目录绝对路径
 */
fun deleteDirection(dir: File?): Boolean {
    if (dir == null || !dir.exists() || dir.isFile) {
        return false
    }

    val listFiles = dir.listFiles()
    if (listFiles.isNullOrEmpty()) {
        return true
    }
    for (file in listFiles) {
        if (file.isFile) {
            file.delete()
        } else if (file.isDirectory) {
            deleteDirection(file) //递归
        }
    }
    dir.delete()
    return true
}


/**
 * 将字符串写入文件
 * @param text  写入的字符串
 * @param fileStr 文件的绝对路径
 * @param isAppend true从尾部写入，false从头覆盖写入
 */
fun writeFile(
    text: String,
    fileStr: String,
    isAppend: Boolean
) {
    try {
        val file = File(fileStr)
        val parentFile = file.parentFile ?: return
        if (parentFile.exists()) {
            parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        val f = FileOutputStream(fileStr, isAppend)
        f.write(text.toByteArray())
        f.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


/**
 * 拷贝文件
 * @param srcPath 绝对路径
 * @param destDir 目标文件所在目录
 * @return boolean true拷贝成功
 */
fun copyFile(srcPath: String, destDir: String): Boolean {
    var flag = false
    val srcFile = File(srcPath) // 源文件
    if (!srcFile.exists()) {
        Log.i("FileUtils is copyFile：", "源文件不存在")
        return false
    }
    // 获取待复制文件的文件名
    val fileName = srcPath.substring(srcPath.lastIndexOf(File.separator))
    val destPath = destDir + fileName
    if (destPath == srcPath) {
        Log.i("FileUtils is copyFile：", "源文件路径和目标文件路径重复")
        return false
    }
    val destFile = File(destPath) // 目标文件
    if (destFile.exists() && destFile.isFile) {
        Log.i("FileUtils is copyFile：", "该路径下已经有一个同名文件")
        return false
    }
    val destFileDir = File(destDir)
    destFileDir.mkdirs()
    try {
        val fis = FileInputStream(srcPath)
        val fos = FileOutputStream(destFile)
        val buf = ByteArray(1024)
        var c: Int
        while (fis.read(buf).also { c = it } != -1) {
            fos.write(buf, 0, c)
        }
        fis.close()
        fos.close()
        flag = true
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return flag
}


/**
 * 重命名文件
 * @param oldPath 旧文件的绝对路径
 * @param newPath 新文件的绝对路径
 * @return 文件重命名成功则返回true
 */
fun renameTo(oldPath: String, newPath: String): Boolean {
    if (oldPath == newPath) {
        Log.i("FileUtils is renameTo：", "文件重命名失败：新旧文件名绝对路径相同")
        return false
    }
    val oldFile = File(oldPath)
    val newFile = File(newPath)
    return oldFile.renameTo(newFile)
}


/**
 * 计算某个文件的大小
 * @param path 文件的绝对路径
 * @return 文件大小
 */
fun getFileSize(path: String): Long {
    val file = File(path)
    return file.length()
}


/**
 * 计算某个文件夹的大小
 * @param  file 目录所在绝对路径
 * @return 文件夹的大小
 */
fun getDirSize(file: File): Double {
    return if (file.exists()) { //如果是目录则递归计算其内容的总大小
        if (file.isDirectory) {
            val children = file.listFiles() ?: return 0.0
            var size = 0.0
            for (f in children) size += getDirSize(f)
            size
        } else { //如果是文件则直接返回其大小,以“兆”为单位
            file.length().toDouble() / 1024 / 1024
        }
    } else {
        0.0
    }
}

/**
 * 获取某个路径下的文件列表
 * @param path 文件路径
 * @return 文件列表File[] files
 */
fun getFileList(path: String): Array<File?>? {
    val file = File(path)
    return if (file.isDirectory) {
        val files = file.listFiles()
        files
    } else {
        null
    }
}


/**
 * 计算某个目录包含的文件数量
 * @param path 目录的绝对路径
 * @return  文件数量
 */
fun getFileCount(path: String): Int {
    val directory = File(path)
    val files = directory.listFiles()
    if (files.isNullOrEmpty()) {
        return 0
    }
    return files.size
}


/**
 * 获取SDCard 总容量大小(MB)
 * @param path 目录的绝对路径
 * @return 总容量大小
 */
fun getSDCardTotal(path: String?): Long {
    return if (null != path && path == "") {
        val statfs = StatFs(path)
        //获取SDCard的Block总数
        val totalBlocks = statfs.blockCount.toLong()
        //获取每个block的大小
        val blockSize = statfs.blockSize.toLong()
        //计算SDCard 总容量大小MB
        totalBlocks * blockSize / 1024 / 1024
    } else {
        0
    }
}


/***
 *
 * @param size 文件大小
 * @return 返回格式好的文件大小
 */
fun formatSize(size: Long): String {
    if (size <= 0) {
        return "0"
    }
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    val digitGroups =
        (log10(size.toDouble()) / log10(1024.0)).toInt()
    return DecimalFormat("#,##0.#").format(
        size / 1024.0.pow(digitGroups.toDouble())
    ).toString() + " " + units[digitGroups]
}

/**
 * 获取SDCard 可用容量大小(MB)
 * @param path 目录的绝对路径
 * @return 可用容量大小
 */
fun getSDCardFree(path: String?): Long {
    return if (null != path && path == "") {
        val statfs = StatFs(path)
        //获取SDCard的Block可用数
        val availaBlocks = statfs.availableBlocks.toLong()
        //获取每个block的大小
        val blockSize = statfs.blockSize.toLong()
        //计算SDCard 可用容量大小MB
        availaBlocks * blockSize / 1024 / 1024
    } else {
        0
    }
}


/**
 * @param path 文件路径
 * @return 读取指定目录下的所有文件的文件内容
 */
fun getFileContent(path: String): String? {
    val file = File(path)
    if (!file.exists()) {
        return null
    }
    val buffer = StringBuffer()
    file.readLines().forEach {
        buffer.append(it)
    }
    return buffer.toString()
}
