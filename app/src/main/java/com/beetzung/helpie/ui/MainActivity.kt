package com.beetzung.helpie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beetzung.helpie.R
import com.beetzung.helpie.core.PermissionType
import com.beetzung.helpie.core.Permissions.REQUEST_CAMERA_PERMISSIONS
import com.beetzung.helpie.core.checkPermission
import com.beetzung.helpie.core.currentFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val type = when (requestCode) {
            REQUEST_CAMERA_PERMISSIONS -> PermissionType.CAMERA
            else -> return
        }
        currentFragment?.onPermissionsResult(type, checkPermission(type))
    }
}