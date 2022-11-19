package com.beetzung.helpie.ui

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.beetzung.helpie.core.PermissionType

abstract class BaseFragment(@LayoutRes id: Int): Fragment(id) {
    open fun onPermissionsResult(permissionType: PermissionType, granted: Boolean) = Unit
}