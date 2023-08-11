package com.ebelli.nopermission

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

object PermissionUtil {
    fun hasPermissions(context: Context, vararg permissions: String, bound: Int? = null): Boolean {
        if (bound == null || Build.VERSION.SDK_INT >= bound) {
            return permissions.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
    }

    fun AppCompatActivity.createActivityResultContract(listener: NoPermissionListener): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            handleResult(this, result, listener)
        }
    }

    fun Fragment.createActivityResultContract(listener: NoPermissionListener): ActivityResultLauncher<Array<String>> {
        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            handleResult(requireActivity(), result, listener)
        }
    }

    private fun handleResult(
        activity: Activity,
        result: Map<String, Boolean>,
        listener: NoPermissionListener
    ) {
        when (getPermissionType(activity, result)) {
            is NoPermissionResulType.Granted -> listener.onPermissionGranted(result.keys.first())
            is NoPermissionResulType.Denied -> listener.onPermissionDenied(result.keys.first())
            is NoPermissionResulType.PermanentlyDenied -> listener.onPermissionPermanentlyDenied(
                result.keys.first()
            )
        }
    }

    private fun getPermissionType(
        activity: Activity,
        result: Map<String, Boolean>
    ): NoPermissionResulType {
        val deniedPermissions = result.filterValues { !it }.keys

        return when {
            deniedPermissions.isEmpty() -> NoPermissionResulType.Granted
            deniedPermissions.any { permission ->
                !shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
            } -> NoPermissionResulType.PermanentlyDenied

            else -> NoPermissionResulType.Denied
        }
    }
}