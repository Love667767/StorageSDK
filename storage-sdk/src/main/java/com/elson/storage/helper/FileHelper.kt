package com.elson.storage.helper

import android.content.Context
import android.net.Uri
import okio.*
import java.io.Closeable
import java.io.File
import java.io.OutputStream

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object FileHelper {

    @JvmStatic
    fun appendPath(file: File?, filePath: String?): File? {
        return if (filePath.isNullOrBlank()) file else file?.resolve(filePath)
    }

    @JvmStatic
    fun mkdirs(file: File?) {
        if (file != null && !file.exists()) {
            file.mkdirs()
        }
    }

    fun getFileSuffix(fileName: String): String? {
        val index = fileName.indexOfLast { '.' == it }
        if (index != -1) {
            return fileName.substring(index + 1)
        }
        return null
    }

    // ---------------------- IO ----------------------


    @JvmStatic
    fun copyPathToDCIM(context: Context, inFile: File?, outUri: Uri?): Boolean {
        if (inFile == null || outUri == null) {
            return false
        }
        try {
            val fileOutputStream = context.contentResolver.openOutputStream(outUri)
            fileOutputStream?.apply {
                return bufferCopy(inFile, this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun bufferCopy(inFile: File, outPutStream: OutputStream): Boolean {
        var inBuffer: BufferedSource? = null
        var outBuffer: BufferedSink? = null
        try {
            inBuffer = inFile.source().buffer()
            outBuffer = outPutStream.sink().buffer()
            outBuffer.writeAll(inBuffer)
            outBuffer.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close(inBuffer)
            close(outPutStream)
            close(outBuffer)
        }
        return false
    }

    @JvmStatic
    fun close(close: Closeable?) {
        try {
            close?.close()
        } catch (e: IOException) {
            // do nothing
        }
    }

}