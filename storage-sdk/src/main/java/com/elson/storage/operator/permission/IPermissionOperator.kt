package com.elson.storage.operator.permission

/**
 * Author : liuqi
 * Date   : 2020/10/22
 * Desc   :
 */
interface IPermissionOperator {

    fun applyPermissions(success: ()-> Unit)
}