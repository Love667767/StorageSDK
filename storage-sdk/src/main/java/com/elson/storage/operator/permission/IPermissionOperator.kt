package com.elson.storage.operator.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Author : liuqi
 * Date   : 2020/10/22
 * Desc   :
 */
interface IPermissionOperator {

    fun applyPermissions(success: ()-> Unit)
}

class DefaultPermissionOperator(val context: Context) : IPermissionOperator {

    override fun applyPermissions(success: () -> Unit) {
        val result = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (result == PackageManager.PERMISSION_GRANTED) {
            success.invoke()
        }
    }
}