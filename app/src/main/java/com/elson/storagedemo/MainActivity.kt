package com.elson.storagedemo

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.*
import com.bumptech.glide.request.transition.Transition
import com.elson.executors.TaskExecutors
import com.elson.storage.StorageFacade
import com.elson.storage.config.StorageConfig
import com.elson.storage.helper.FileHelper
import com.elson.storage.helper.MediaHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StorageFacade.init(StorageConfig("Elson").apply {
            mMainExecutor = TaskExecutors.mainThread()
            mIOExecutorService = TaskExecutors.io()
        })

        saveFileTv.setOnClickListener {
            glideImgFile { file ->
//                StorageFacade.storeImgPublishDir(this, file, "CBA.jpg").start()

                StorageFacade.with(this)
                        .load(file)
//                        .load(BitmapFactory.decodeFile(""))
//                        .setExtAppFileDir("Elson")
//                        .setExtAppCacheDir("Elson")
                        .setExtPublishDir("/Elson1", "/Elson2")
                        .setOutputFileName(FileHelper.getImageFileName("ABC.jpg"))
                        .asImage()
//                        .asDownload()
                        .synSystemMedia()
                        .start()
            }
        }

        saveBitmapTv.setOnClickListener {

            glideImgBitmap { bitmap ->
                StorageFacade.storeVideoPublishDir(this, bitmap, "CBA.mp4")

//                StorageFacade.with(this)
//                        .load(bitmap)
////                        .setExtAppFileDir("Elson")
//                        .setExtPublishDir("/Elson/Image", "Elson_Q")
//                        .setOutputFileName(FileHelper.getImageFileName("ABC.webp"))
////                        .asImage()
////                        .asDownload()
//                        .synSystemMedia()
//                        .start()
            }
        }

        if (!MediaHelper.checkAndroid_Q()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        }
    }

    private fun glideImgFile(callback: (File) -> Unit) {
        val path1 = "https://image.32yx.com/file/userfiles/images/2017080820292511601.jpg"
        Glide.with(this)
                .downloadOnly()
                .load(path1)
                .into(object : SimpleTarget<File?>() {
                    override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                        callback.invoke(resource)
                    }
                })
    }

    private fun glideImgBitmap(callback: (Bitmap) -> Unit) {
        val path1 = "https://image.32yx.com/file/userfiles/images/2017080820292511601.jpg"
        Glide.with(this)
                .asBitmap()
                .load(path1)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        callback.invoke(resource)
                    }
                })
    }
}
