package com.elson.storage.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import okio.*
import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.io.OutputStream

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object FileHelper {

    @JvmStatic
    fun getImageFileName(fileName: String?): String {
        return if (fileName.isNullOrBlank()) "IMG_${System.currentTimeMillis()}.jpeg" else fileName
    }

    @JvmStatic
    fun getVideoFileName(fileName: String?): String {
        return if (fileName.isNullOrBlank()) "VIDEO_${System.currentTimeMillis()}.mp4" else fileName
    }



    @JvmStatic
    fun appendPath(firstPath: String, secondPath: String?): String {
        if (secondPath.isNullOrBlank()) {
            return firstPath
        }
        return firstPath.plus(File.separator).plus(secondPath)
    }

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

    @JvmStatic
    fun deleteFile(file: File?) {
        if (file != null && file.isFile && file.exists()) {
            file.delete()
        }
    }

    @JvmStatic
    fun getFileSuffix(fileName: String): String? {
        val index = fileName.indexOfLast { '.' == it }
        if (index != -1) {
            return fileName.substring(index + 1)
        }
        return null
    }

    @JvmStatic
    fun getFileName(absolutePath: String?): String? {
        if (absolutePath.isNullOrBlank()) {
            return null
        }
        return absolutePath.substring(absolutePath.lastIndexOf("/") + 1)
    }


    // ---------------------- IO ----------------------

    /**
     * 将Bitmap保存到公共目录
     */
    @JvmStatic
    fun saveBitmap2ExternalDir(context: Context, bitmap: Bitmap?, outputUri: Uri?): Boolean {
        if (bitmap == null || outputUri == null) {
            return false
        }
        try {
            val fileOutputStream = context.contentResolver.openOutputStream(outputUri)
            if (fileOutputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                close(fileOutputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun saveBitmap2ExternalDir(context: Context, bitmap: Bitmap?, outputFile: File?): Boolean {
        if (bitmap == null || outputFile == null) {
            return false
        }
        try {
            val fileOutputStream = outputFile.outputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            close(fileOutputStream)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun saveFile2ExternalDir(context: Context, inputFile: File?, outputUri: Uri?): Boolean {
        if (inputFile == null || outputUri == null) {
            return false
        }
        try {
            val fileOutputStream = context.contentResolver.openOutputStream(outputUri)
            if (fileOutputStream != null) {
                return bufferCopy(inputFile, fileOutputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun saveFile2ExternalDir(inputFile: File?, outputFile: File?): Boolean {
        if (inputFile == null || outputFile == null) {
            return false
        }
        try {
            val fileOutputStream = outputFile.outputStream()
            return bufferCopy(inputFile, fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * 将公共目录的文件拷贝到沙盒目录
     */
    @JvmStatic
    fun copyFile2ExternalAppDir(context: Context, inputUri: Uri?, outputFile: File?): Boolean {
        if (outputFile == null || inputUri == null) {
            return false
        }
        try {
            val fileInputStream = context.contentResolver.openInputStream(inputUri)
            if (fileInputStream != null) {
                bufferCopy(fileInputStream, outputFile)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun bufferCopy(inputStream: InputStream, outputFile: File): Boolean {
        var inBuffer: BufferedSource? = null
        var outBuffer: BufferedSink? = null
        try {
            inBuffer = inputStream.source().buffer()
            outBuffer = outputFile.sink().buffer()
            outBuffer.writeAll(inBuffer)
            outBuffer.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close(inBuffer)
            close(inputStream)
            close(outBuffer)
        }
        return false
    }

    @JvmStatic
    fun bufferCopy(inputFile: File, outputStream: OutputStream): Boolean {
        var inBuffer: BufferedSource? = null
        var outBuffer: BufferedSink? = null
        try {
            inBuffer = inputFile.source().buffer()
            outBuffer = outputStream.sink().buffer()
            outBuffer.writeAll(inBuffer)
            outBuffer.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close(inBuffer)
            close(outputStream)
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