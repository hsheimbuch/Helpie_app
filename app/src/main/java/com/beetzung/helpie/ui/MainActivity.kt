package com.beetzung.helpie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.beetzung.helpie.R
import com.beetzung.helpie.core.*
import com.beetzung.helpie.core.Permissions.REQUEST_CAMERA_PERMISSIONS
import com.beetzung.helpie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.bind(findViewById(R.id.root_layout))
        binding.setupView()
    }

    private fun ActivityMainBinding.setupView() {
        activityBottomAppBar.setNavigationOnClickListener {

        }
        activityBottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    Log.d(TAG, "setupView: home")
                    true
                }
                else -> false
            }
        }
        activityFab.setOnClickListener {
            navController.navigate(R.id.action_global_cameraFragment)
        }
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