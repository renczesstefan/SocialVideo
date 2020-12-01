package com.social.socialvideo.fragments

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.social.socialvideo.R
import com.social.socialvideo.databinding.RecordBinding
import com.social.socialvideo.enums.ServerResponse
import com.social.socialvideo.services.SessionManager
import com.social.socialvideo.utils.PathUtils
import com.social.socialvideo.viewModels.RecordViewModel


class RecordVideoFragment : Fragment() {
    private lateinit var binding: RecordBinding
    private lateinit var recordViewModel: RecordViewModel
    val REQUEST_VIDEO_CAPTURE = 1
    val GALLERY_REQUEST = 9
    private lateinit var sessionManager: SessionManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sessionManager = SessionManager(this.requireContext())
        binding = DataBindingUtil.inflate(
            inflater, R.layout.record, container, false
        )

        recordViewModel = ViewModelProvider(this).get(RecordViewModel::class.java)
        binding.recordViewModel = recordViewModel

        recordViewModel.uploadStatus.observe(viewLifecycleOwner, Observer { status ->
            if (status != ServerResponse.DEFAULT) {
                resolveUploadStatus(status)
                recordViewModel.resetState()
            }
        })

        openDialog()
        return binding.root
    }

    private fun openDialog() {
        val items = arrayOf("Gallery", "Camera")
        val builder = AlertDialog.Builder(this.requireContext())
        with(builder)
        {
            setTitle("Choose video from gallery or open camera")
            setItems(items) { dialog, which -> resolveDialogResult(which) }
            show()
        }
    }

    private fun resolveUploadStatus(status: ServerResponse?) {
        when (status) {
            ServerResponse.SERVER_SUCCESS -> {
                Toast.makeText(context, "Video was successfully uploaded!", Toast.LENGTH_SHORT)
                    .show()
                this.findNavController()
                    .navigate(R.id.action_recordVideoFragment_to_postsFragment)
            }
            ServerResponse.SERVER_ERROR -> {
                Toast.makeText(context, "Upload of profile picture failed!", Toast.LENGTH_SHORT)
                    .show()
                this.findNavController()
                    .navigate(R.id.action_recordVideoFragment_to_postsFragment)
            }
            else -> {
            }
        }
    }

    /*Creating intent to tell external activity to open camera only for video capturing*/
    private fun dispatchTakeVideoIntent() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            takeVideoIntent.resolveActivity(this.requireContext().packageManager)?.also {
                startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE)
            }
        }
    }

    /*Will be called whether the camera or gallery activity finished it's process*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_VIDEO_CAPTURE || requestCode == GALLERY_REQUEST) && resultCode == RESULT_OK && data != null) {
            val videoUri: Uri = data?.data!!
            val path: String = PathUtils.getPath(this.requireContext(), videoUri)!!
            recordViewModel.upload(path, sessionManager.fetchAuthToken()!!)
        } else {
            this.findNavController()
                .navigate(R.id.action_recordVideoFragment_to_postsFragment)
        }
    }

    private fun resolveDialogResult(item: Int) {
        if (item == 0) {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "video/*"
            startActivityForResult(intent, GALLERY_REQUEST)
        } else if (item == 1) {
            dispatchTakeVideoIntent()
        }
    }
}