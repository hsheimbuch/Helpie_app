package com.beetzung.helpie.core

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.beetzung.helpie.R
import com.beetzung.helpie.ui.BaseFragment

val AppCompatActivity.currentFragment: BaseFragment?
    get() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.activity_nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0) as? BaseFragment
    }

val Any.TAG: String
    get() = this::class.java.simpleName

val Fragment.navController: NavController
    get() = findNavController()

val AppCompatActivity.navController: NavController
    get() = findNavController(R.id.activity_nav_host_fragment)