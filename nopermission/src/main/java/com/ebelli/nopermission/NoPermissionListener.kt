package com.ebelli.nopermission

interface NoPermissionListener {
    fun onPermissionDenied(permission: String)
    fun onPermissionPermanentlyDenied(permission: String)
    fun onPermissionGranted(permission: String)
}