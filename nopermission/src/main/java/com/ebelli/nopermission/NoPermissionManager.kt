package com.ebelli.nopermission

import android.content.Context

class NoPermissionManager(private val context: Context) {
    companion object {
        fun initialize(context: Context): NoPermissionManager = NoPermissionManager(context)
    }

    fun hasPermissions(vararg permissions: String, bound: Int? = null): Boolean {
        return PermissionUtil.hasPermissions(context, *permissions, bound = bound)
    }
}