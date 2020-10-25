package com.elson.storage.helper

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import okio.*
import java.io.Closeable
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object FileHelper {

    @JvmStatic
    fun getImageFileName(fileName: String? = null): String {
        return if (fileName.isNullOrBlank()) "IMG_${System.currentTimeMillis()}.jpeg" else fileName
    }

    @JvmStatic
    fun getVideoFileName(fileName: String? = null): String {
        return if (fileName.isNullOrBlank()) "VIDEO_${System.currentTimeMillis()}.mp4" else fileName
    }

    @JvmStatic
    fun appendPath(firstPath: String, secondPath: String?): String {
        if (secondPath.isNullOrBlank()) {
            return firstPath
        }
        return if (secondPath.startsWith(File.separator)) {
            firstPath.plus(secondPath)
        } else {
            firstPath.plus(File.separator).plus(secondPath)
        }
    }


    @JvmStatic
    fun appendPath(file: File?, filePath: String?): File? {
        return if (filePath.isNullOrBlank()) {
            file
        } else {
            return if (filePath.startsWith(File.separator)) {
                file?.resolve(filePath.substring(1))
            } else {
                file?.resolve(filePath)
            }
        }
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
    fun deleteFile(context: Context, fileName: String?): Int {
        if (!fileName.isNullOrBlank()) {
            return context.contentResolver.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MediaStore.Images.Media.DISPLAY_NAME + "=?", arrayOf(fileName))
        }
        return 0
    }

    @JvmStatic
    fun getFileSuffix(file: String): String {
        val index = file.indexOfFirst { '?' == it }
        var fileName = file
        if (index != -1) {
            fileName = file.substring(0, index)
        }
        val dotIndex = fileName.indexOfLast { '.' == it }
        if (dotIndex != -1) {
            return fileName.substring(dotIndex).toLowerCase(Locale.ROOT)
        }
        return ""
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
            val fileOutputStream = context.contentResolver.openOutputStream(outputUri, "wa")
            if (fileOutputStream != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                close(fileOutputStream)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun saveBitmap2ExternalDir(bitmap: Bitmap?, outputFile: File?): Boolean {
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
                return bufferCopy(inputFile.inputStream(), fileOutputStream)
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
            return bufferCopy(inputFile, outputFile)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun saveSource2ExternalDir(context: Context, source: Source?, outputUri: Uri?): Boolean {
        if (source == null || outputUri == null) {
            return false
        }
        try {
            val outputStream = context.contentResolver.openOutputStream(outputUri)
            if (outputStream != null) {
                return bufferCopy(source, outputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun saveSource2ExternalDir(source: Source?, outputFile: File?): Boolean {
        if (source == null || outputFile == null) {
            return false
        }
        try {
            return bufferCopy(source, outputFile.outputStream())
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
                bufferCopy(fileInputStream, outputFile.outputStream())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    @JvmStatic
    fun bufferCopy(source: Source, outputStream: OutputStream): Boolean {
        var inBuffer: BufferedSource? = null
        var outBuffer: BufferedSink? = null
        try {
            inBuffer = source.buffer()
            outBuffer = outputStream.sink().buffer()
            outBuffer.writeAll(inBuffer)
            outBuffer.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close(inBuffer)
            close(outBuffer)
            close(source)
            close(outputStream)
        }
        return false
    }

    @JvmStatic
    fun bufferCopy(inputFile: File, outputFile: File): Boolean {
        var inBuffer: BufferedSource? = null
        var outBuffer: BufferedSink? = null
        try {
            inBuffer = inputFile.source().buffer()
            outBuffer = outputFile.sink().buffer()
            outBuffer.writeAll(inBuffer)
            outBuffer.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close(inBuffer)
            close(outBuffer)
        }
        return false
    }

    @JvmStatic
    fun bufferCopy(inputStream: InputStream, outputStream: OutputStream): Boolean {
        var inBuffer: BufferedSource? = null
        var outBuffer: BufferedSink? = null
        try {
            inBuffer = inputStream.source().buffer()
            outBuffer = outputStream.sink().buffer()
            outBuffer.writeAll(inBuffer)
            outBuffer.flush()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close(inBuffer)
            close(outBuffer)
            close(inputStream)
            close(outputStream)
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