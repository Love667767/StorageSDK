package com.elson.storage.helper

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.elson.storage.StorageFacade
import java.io.File
import java.util.*


/**
 * Author : Elson
 * Date   : 2020/10/21
 * Desc   :
 */
object MediaHelper {

    fun checkAndroid_Q(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && !Environment.isExternalStorageLegacy()
    }


    // -------------- Android Legacy ------------

    @JvmStatic
    fun insertImage(context: Context, file: File) {
        MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, file.name, file.name)
    }

    @JvmStatic
    fun insertImage(context: Context, absolutePath: String, width: Int = 0, height: Int = 0) {
        try {
            val file = File(absolutePath)
            val values = initCommonContentValues(file)
            values.put(MediaStore.MediaColumns.MIME_TYPE, MimeTypeHelper.getImageMimeType(absolutePath))
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                if (width > 0) values.put(MediaStore.Images.ImageColumns.WIDTH, width)
                if (height > 0) values.put(MediaStore.Images.ImageColumns.HEIGHT, height)
            }
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            StorageFacade.scanFile(context, file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 保存到视频到本地，并插入MediaStore以保证相册可以查看到,这是更优化的方法，防止读取的视频获取不到宽高
     */
    @JvmStatic
    fun insertVideo(context: Context, filePath: String, width: Int, height: Int, duration: Long = 0) {
        try {
            val file = File(filePath)
            val values = initCommonContentValues(file)
            values.put(MediaStore.MediaColumns.MIME_TYPE, MimeTypeHelper.getVideoMimeType(filePath))
            if (duration > 0) {
                values.put(MediaStore.Video.VideoColumns.DURATION, duration)
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
                if (width > 0) values.put(MediaStore.Video.VideoColumns.WIDTH, width)
                if (height > 0) values.put(MediaStore.Video.VideoColumns.HEIGHT, height)
            }
            context.contentResolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values)
            StorageFacade.scanFile(context, file)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 针对非系统文件夹下的文件,使用该方法
     * 插入时初始化公共字段
     */
    @JvmStatic
    private fun initCommonContentValues(saveFile: File): ContentValues {
        val timeMillis = System.currentTimeMillis()
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.TITLE, saveFile.name)
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.name)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, timeMillis)
        values.put(MediaStore.MediaColumns.DATE_ADDED, timeMillis)
        values.put(MediaStore.MediaColumns.DATA, saveFile.absolutePath)
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length())
        values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, timeMillis)
        return values
    }

    // ----------------- Android Q --------------

    @JvmStatic
    fun insertImageQ(context: Context, fileName: String, relativePath: String = Environment.DIRECTORY_PICTURES): Uri? {
        try {
            val values = initCommonContentValuesQ(fileName, relativePath)
            values.put(MediaStore.MediaColumns.MIME_TYPE, MimeTypeHelper.getImageMimeType(fileName))
            val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val resolver: ContentResolver = context.contentResolver
            return resolver.insert(external, values)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    fun insertAudioQ(context: Context, fileName: String, relativePath: String = Environment.DIRECTORY_MUSIC): Uri? {
        try {
            val values = initCommonContentValuesQ(fileName, relativePath)
            values.put(MediaStore.MediaColumns.MIME_TYPE, MimeTypeHelper.getAudioMimeType(fileName))
            val external = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val resolver: ContentResolver = context.contentResolver
            return resolver.insert(external, values)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.Q)
    fun insertVideoQ(context: Context, fileName: String, duration: Long = 0, relativePath: String = Environment.DIRECTORY_MOVIES): Uri? {
        try {
            val values = initCommonContentValuesQ(fileName, relativePath)
            values.put(MediaStore.MediaColumns.MIME_TYPE, MimeTypeHelper.getVideoMimeType(fileName))
            if (duration > 0) {
                values.put(MediaStore.Video.VideoColumns.DURATION, duration)
            }
            val external = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val resolver: ContentResolver = context.contentResolver
            return resolver.insert(external, values)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    @RequiresApi(Build.VERSION_CODES.Q)
    fun insertDownloadQ(context: Context, fileName: String): Uri? {
        try {
            val values = initCommonContentValuesQ(fileName, Environment.DIRECTORY_DOWNLOADS)
            values.put(MediaStore.MediaColumns.MIME_TYPE, FileHelper.getFileSuffix(fileName))
            val external = MediaStore.Downloads.EXTERNAL_CONTENT_URI
            val resolver: ContentResolver = context.contentResolver
            return resolver.insert(external, values)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @JvmStatic
    private fun initCommonContentValuesQ(fileName: String, relativePath: String): ContentValues {
        val timeMillis = System.currentTimeMillis()
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.TITLE, fileName)
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, timeMillis)
        values.put(MediaStore.MediaColumns.DATE_ADDED, timeMillis)
        // Android Q 文件存放路径
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, timeMillis)
        return values
    }

    @JvmStatic
    fun getImageUriFromPath(context: Context, absolutePath: String): Uri? {
        var cursor: Cursor? = null
        try {
            val projection = arrayOf(MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.DISPLAY_NAME)
            val selectionArgs = arrayOf(FileHelper.getFileName(absolutePath))

            val mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
             cursor = context.contentResolver.query(mediaUri, projection,
                    MediaStore.Images.Media.DISPLAY_NAME + "= ?", selectionArgs,
                    null)
            var uri: Uri? = null
            if (cursor == null) return null
            if (cursor.moveToFirst()) {
                uri = ContentUris.withAppendedId(mediaUri,
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)))
            }

            return uri
        }catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

    @JvmStatic
    private fun getDataColumn(context: Context, uri: Uri, selection: String?,
                      selectionArgs: Array<String?>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
                column
        )
        try {
            cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        } finally {
            cursor?.close()
        }
        return null
    }

}