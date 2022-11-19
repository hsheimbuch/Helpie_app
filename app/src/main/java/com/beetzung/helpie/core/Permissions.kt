package com.beetzung.helpie.core

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.checkPermission(permissionType: PermissionType): Boolean =
    requireContext().checkPermission(permissionType)

fun Context.checkPermission(permissionType: PermissionType): Boolean = when (permissionType) {
    PermissionType.CAMERA -> Permissions.CAMERA_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }
}

fun Fragment.requestPermissions(permissionType: PermissionType) = when (permissionType) {
    PermissionType.CAMERA -> ActivityCompat.requestPermissions(
        (requireActivity()),
        Permissions.CAMERA_PERMISSIONS,
        Permissions.REQUEST_CAMERA_PERMISSIONS
    )
}


enum class PermissionType {
    CAMERA
}

object Permissions {
    const val REQUEST_CAMERA_PERMISSIONS = 10

    val CAMERA_PERMISSIONS =
        mutableListOf(CAMERA, RECORD_AUDIO).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

}