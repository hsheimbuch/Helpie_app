package com.beetzung.helpie.ui.scan

import android.os.Bundle
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.databinding.FragmentCameraBinding
import com.beetzung.helpie.core.PermissionType
import com.beetzung.helpie.core.TAG
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
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraListener = Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }
        cameraProviderFuture.addListener(cameraListener, ContextCompat.getMainExecutor(requireContext()))
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onPermissionsResult(permissionType: PermissionType, granted: Boolean) {
        if (permissionType == PermissionType.CAMERA && granted) {
            startCamera()
        } else {
            //TODO handle denied permission
        }
    }
}