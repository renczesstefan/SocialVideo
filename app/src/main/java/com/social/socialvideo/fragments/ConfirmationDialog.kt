package com.social.socialvideo.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.social.socialvideo.R
import com.social.socialvideo.utils.REQUEST_VIDEO_PERMISSIONS
import com.social.socialvideo.utils.VIDEO_PERMISSIONS

class ConfirmationDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(activity)
            .setMessage("Requesting permissions")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                parentFragment?.requestPermissions(VIDEO_PERMISSIONS,
                    REQUEST_VIDEO_PERMISSIONS)
            }
            .setNegativeButton(android.R.string.cancel) { _,_ ->
                parentFragment?.activity?.finish()
            }
            .create()

}