package com.elson.storagedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.elson.storage.StorageFacade
import com.elson.storage.operator.file.IFileOperator
import com.elson.storage.operator.media.ImageMediaOperator
import com.elson.storage.request.BaseRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView.setOnClickListener {

            StorageFacade.with(this)
                .setExternalCacheDir()
                .asImage()
                .setMediaOperator(ImageMediaOperator())
                .start(object : IFileOperator {
                    override fun operateFile(request: BaseRequest) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun operateFile_Q(request: BaseRequest) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                })
        }
    }
}
