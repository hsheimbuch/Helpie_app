package com.beetzung.helpie.core

import androidx.appcompat.app.AppCompatActivity
import com.beetzung.helpie.R
import com.beetzung.helpie.ui.BaseFragment

val AppCompatActivity.currentFragment: BaseFragment?
    get() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        return navHostFragment?.childFragmentManager?.fragments?.get(0) as? BaseFragment
    }