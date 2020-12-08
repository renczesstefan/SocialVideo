package com.social.socialvideo.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * Zorazenie permissions pre kameru
 */
class ErrorDialog : DialogFragment() {
    private val ARG_MESSAGE = "message"

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog =
        AlertDialog.Builder(activity)
            .setMessage(arguments?.getString(ARG_MESSAGE))
            .setPositiveButton(android.R.string.ok)  { _, _ -> activity?.finish() }
            .create()

    companion object {
        fun newInstance(message: String) = ErrorDialog().apply {
            arguments = Bundle().apply { putString(ARG_MESSAGE, message) }
        }
    }

}