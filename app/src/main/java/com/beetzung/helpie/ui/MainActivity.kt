package com.beetzung.helpie.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.navigateUp
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
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.bind(findViewById(R.id.root_view))
        binding.setupView()
    }

    private fun ActivityMainBinding.setupView() {
        setSupportActionBar(activityToolbar)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings)
        )
        setupActionBarWithNavController(this@MainActivity, navController, appBarConfiguration)
        activityNavView.setupWithNavController(navController)
        activityToolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, b ->
            when (destination.id) {
                R.id.navigation_dashboard,
                R.id.navigation_home,
                R.id.navigation_settings -> {
                    activityNavView.visibility = View.VISIBLE
                }
                else -> {
                    activityNavView.visibility = View.GONE
                }
            }
            when (destination.id) {
                R.id.navigation_advice,
                R.id.navigation_feelings -> {
                    activityToolbar.visibility = View.VISIBLE
                }
                else -> {
                    activityToolbar.visibility = View.GONE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
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