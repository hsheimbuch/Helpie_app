package com.beetzung.helpie.ui.scan

import android.app.AlertDialog
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.core.*
import com.beetzung.helpie.data.model.ApiEmotion
import com.beetzung.helpie.data.model.Emotion
import com.beetzung.helpie.databinding.FragmentCameraBinding
import com.beetzung.helpie.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class CameraFragment : BaseFragment(R.layout.fragment_camera) {
    private val binding: FragmentCameraBinding by viewBinding(FragmentCameraBinding::bind)
    private val viewModel: CameraViewModel by viewModels()

    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var cameraProvider: ProcessCameraProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraExecutor = Executors.newSingleThreadExecutor()
        if (checkPermission(PermissionType.CAMERA)) {
            startCamera()
        } else {
            requestPermissions(PermissionType.CAMERA)
        }
        binding.setupView()
        viewModel.imageSent.observe(viewLifecycleOwner) { event ->
            event.handle { emotion ->
                hideLoadingDialog()
                emotion?.let {
                    navigateToFeelingsScreen(it)
                } ?: showBadFaceDialog()
            }
        }
    }

    private fun showBadFaceDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Bad face")//R.string.bad_face_title)
            .setMessage("Bad face")//R.string.bad_face_message)
            .setPositiveButton(R.string.button_try_again, null)
            .show()
    }

    private fun navigateToFeelingsScreen(emotion: ApiEmotion) {
        navController.navigate(CameraFragmentDirections.actionCameraFragmentToFeelingsFragment(emotion))
    }

    private fun FragmentCameraBinding.setupView() {
        cameraButtonCapture.setOnClickListener {
            if (checkPermission(PermissionType.CAMERA)) {
                takePhoto()
            } // TODO ???
        }
        cameraButtonClose.setOnClickListener {
            pop()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val cameraListener = Runnable {
            if (!this::cameraProvider.isInitialized) {
                cameraProvider = cameraProviderFuture.get()
            }
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
                onError(exc.message)
            }
        }
        cameraProviderFuture.addListener(
            cameraListener,
            ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun pauseCamera() {
        cameraProvider.unbindAll()
    }

    private fun onError(text: String?) {
        hideLoadingDialog()
        Toast.makeText(requireContext(), text ?: "Unknown error", Toast.LENGTH_SHORT).show()
        startCamera()
    }

    private fun takePhoto() {
        Log.d(TAG, "takePhoto start")
        showLoadingDialog()

        val imageCapture = imageCapture ?: return onError("Image capture is null")
        val name = System.currentTimeMillis().toString() + ".jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
        val outputOptions = ImageCapture.OutputFileOptions.Builder(
            requireActivity().contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
            .build()
        val callback = object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                onError(exc.message)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val msg = "Photo capture succeeded: ${output.savedUri}"
                pauseCamera()
                Log.d(TAG, msg)
                output.savedUri?.let { uri ->
                    val bytes = getImageBytes(uri) ?: return onError("Image is null")
                    viewModel.sendImage(bytes)
                } ?: onError("Uri is null")
            }
        }
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            callback
        )
    }

    private fun getImageBytes(uri: Uri): ByteArray? {
        val inputStream: InputStream =
            requireActivity().contentResolver.openInputStream(uri) ?: return null
        val byteBuffer = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
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