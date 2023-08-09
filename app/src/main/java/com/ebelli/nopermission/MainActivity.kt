package com.ebelli.nopermission

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.ebelli.nopermission.PermissionUtil.createActivityResultContract

class MainActivity : AppCompatActivity(), NoPermissionListener {
    private val noPermissionManager: NoPermissionManager by lazy {
        NoPermissionManager.initialize(this.applicationContext)
    }

    private val cameraLauncher = createActivityResultContract(this)
    private val callLauncher = createActivityResultContract(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<AppCompatButton>(R.id.cameraButton)
        val callButton = findViewById<AppCompatButton>(R.id.callButton)
        button.setOnClickListener {
            if (noPermissionManager.hasPermissions(android.Manifest.permission.CAMERA).not()) {
                cameraLauncher.launch(arrayOf(android.Manifest.permission.CAMERA))
            }
        }

        callButton.setOnClickListener {
            if (noPermissionManager.hasPermissions(
                    android.Manifest.permission.CALL_PHONE,
                    bound = Build.VERSION_CODES.M
                ).not()
            ) {
                callLauncher.launch(arrayOf(android.Manifest.permission.CALL_PHONE))
            } else {
                notify("you also has permissions")
            }
        }
    }

    override fun onPermissionDenied(permission: String) {
        when (permission) {
            android.Manifest.permission.CAMERA -> {
                notify("camera Permission was denied")
            }
            android.Manifest.permission.CALL_PHONE -> {
                notify("call Permission was denied")
            }
        }
    }

    override fun onPermissionPermanentlyDenied(permission: String) {
        when (permission) {
            android.Manifest.permission.CAMERA -> {
                notify("camera Permission was permanently denied")
            }
            android.Manifest.permission.CALL_PHONE -> {
                notify("call Permission was permanently denied")
            }
        }
    }

    override fun onPermissionGranted(permission: String) {
        when (permission) {
            android.Manifest.permission.CAMERA -> {
                notify("camera Permission granted")
            }
            android.Manifest.permission.CALL_PHONE -> {
                notify("call Permission granted")
            }
        }
    }

    private fun notify(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }
}