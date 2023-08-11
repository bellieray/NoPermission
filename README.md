

## NoPermission [![](https://jitpack.io/v/bellieray/NoPermission.svg)](https://jitpack.io/#bellieray/NoPermission)

## Implementation
```kotlin
allprojects { 
    repositories {
        maven { url "https://jitpack.io" }
    }
}

dependencies {
	        implementation 'com.github.bellieray:NoPermission:1.0.3'
	}
```

```kotlin
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
```

**1.** Initialize manager basically.

**2.** Create launcher for every permission.

**3.** Check has permission and  start launch.

**4.** If permission depends on the  build version, you can define the build version int the bound parameter.

**5.** Don't forget to inherit listener to fragment or activity.


## LICENSE
```
Copyright 2023 Eray Belli

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```