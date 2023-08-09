package com.ebelli.nopermission

sealed class NoPermissionResulType {
    object Granted : NoPermissionResulType()
    object Denied : NoPermissionResulType()
    object PermanentlyDenied : NoPermissionResulType()
}