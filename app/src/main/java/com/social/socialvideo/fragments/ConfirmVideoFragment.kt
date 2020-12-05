package com.social.socialvideo.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.ConfirmVideoBinding
import com.social.socialvideo.databinding.LoginBinding
import com.social.socialvideo.services.SessionManager
import com.social.socialvideo.viewModels.ConfirmVideoViewModel
import com.social.socialvideo.viewModels.LoginViewModel

class ConfirmVideoFragment : Fragment() {

    private lateinit var binding: ConfirmVideoBinding
    private lateinit var confirmVideoViewModel: ConfirmVideoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val sessionManager = SessionManager(this.requireContext())
        binding = DataBindingUtil.inflate(
            inflater, R.layout.confirm_video, container, false)
        confirmVideoViewModel = ViewModelProvider(this).get(ConfirmVideoViewModel::class.java)
        binding.confirmVideoModel = confirmVideoViewModel

        var videoUri = arguments?.getString("videoUri")

        binding.videoView.setVideoURI(Uri.parse(videoUri))
        binding.videoView.requestFocus();
        binding.videoView.start();

        confirmVideoViewModel.confirmed.observe(viewLifecycleOwner, Observer { confirmed ->
            if (confirmed) {
                val bundle = bundleOf("videoUri" to videoUri)
                this.findNavController()
                    .navigate(R.id.action_confirmVideoFragment_to_newPostFragment, bundle)
            }
        })
        return binding.root
    }
}