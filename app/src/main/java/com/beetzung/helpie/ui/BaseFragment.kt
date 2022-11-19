package com.beetzung.helpie.ui

import android.app.AlertDialog
import android.view.ContextThemeWrapper
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.beetzung.helpie.R
import com.beetzung.helpie.core.PermissionType

abstract class BaseFragment(@LayoutRes id: Int): Fragment(id) {
    private var dialog: AlertDialog? = null
    open fun onPermissionsResult(permissionType: PermissionType, granted: Boolean) = Unit

    fun showLoadingDialog() {
        if (dialog?.isShowing == true) {
            return
        }
        val context = ContextThemeWrapper(requireContext(), R.style.Helpie)
        dialog = AlertDialog.Builder(context)
            .setView(ProgressBar(context))
            .setCancelable(false)
            .show()
    }

    fun hideLoadingDialog() {
        dialog?.dismiss()
    }
}