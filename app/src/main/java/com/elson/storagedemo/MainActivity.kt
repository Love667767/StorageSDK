package com.elson.storagedemo

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.*
import com.bumptech.glide.request.transition.Transition
import com.elson.storage.StorageFacade
import com.elson.storage.config.StorageConfig
import com.elson.storage.helper.MediaHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        StorageFacade.init(StorageConfig().apply {
            mRootDir = "Elson"
            mIOExecutorService = Executors.newFixedThreadPool(5, object : ThreadFactory {
                override fun newThread(r: Runnable): Thread {
                    return Thread(r).apply {
                        name = "Elson"
                    }
                }
            })
        })
        textView.setOnClickListener {

            glideImg { file ->
                StorageFacade.with(this)
                        .setInputFile(file)
                        .setExternalPublishDir("soul")
                        .setOutputFileName("封面1.jpeg")
                        .asImage()
                        .synSystemMedia()
                        .start()
            }

        }

        if (!MediaHelper.checkAndroid_Q()) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE), 1000)
        }
    }

    private fun glideImg(callback: (File) -> Unit) {
        val path1 = "https://Elson.png"
        Glide.with(this)
                .downloadOnly()
                .load(path1)
                .into(object : SimpleTarget<File?>() {
                    override fun onResourceReady(resource: File, transition: Transition<in File?>?) {
                        callback.invoke(resource)
                    }
                })
    }
}
