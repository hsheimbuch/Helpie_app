package com.beetzung.helpie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.beetzung.helpie.R
import com.beetzung.helpie.core.*
import com.beetzung.helpie.core.Permissions.REQUEST_CAMERA_PERMISSIONS
import com.beetzung.helpie.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.bind(findViewById(R.id.root_view))
        binding.setupView()
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_dashboard,
                R.id.navigation_home,
                R.id.navigation_settings -> binding.activityNavView.visibility = View.VISIBLE
                else ->
                    binding.activityNavView.visibility = View.GONE
            }
        }
    }

    private fun ActivityMainBinding.setupView() {
        activityNavView.setupWithNavController(navController)
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