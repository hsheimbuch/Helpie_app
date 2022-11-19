package com.beetzung.helpie.ui.scan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.beetzung.helpie.R
import com.beetzung.helpie.databinding.FragmentCameraBinding

class CameraFragment : Fragment(R.layout.fragment_camera) {
    private val binding: FragmentCameraBinding by viewBinding(FragmentCameraBinding::bind)
    private val viewModel: CameraViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}