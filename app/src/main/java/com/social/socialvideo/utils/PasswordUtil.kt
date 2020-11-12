package com.social.socialvideo.utils

import android.content.Context
import android.widget.Toast

class PasswordUtil {
    companion object {
        fun checkMatchingPasswords(
            password1: String,
            password2: String,
            context: Context?
        ): Boolean {
            if (!password1.equals(password2)) {
                Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }
    }
}