package com.beetzung.helpie.ui.scan

import android.os.Bundle
import androidx.camera.core.ImageCapture
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.databinding.FragmentCameraBinding
import com.beetzung.helpie.core.PermissionType
import com.beetzung.helpie.core.checkPermission
import com.beetzung.helpie.core.requestPermissions
import com.beetzung.helpie.ui.BaseFragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : BaseFragment(R.layout.fragment_camera) {
    private val binding: FragmentCameraBinding by viewBinding(FragmentCameraBinding::bind)
    private val viewModel: CameraViewModel by viewModels()

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        if (checkPermission(PermissionType.CAMERA)) {
            startCamera()
        } else {
            requestPermissions(PermissionType.CAMERA)
        }
    }

    private fun startCamera() {

    }

    override fun onPermissionsResult(permissionType: PermissionType, granted: Boolean) {
        if (permissionType == PermissionType.CAMERA && granted) {
            startCamera()
        } else {
            //TODO handle denied permission
        }
    }
}