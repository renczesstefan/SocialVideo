package com.social.socialvideo.utils

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PasswordUtil {
    companion object {
        fun checkMatchingPasswords(
            password1: String?,
            password2: String?
        ): Boolean {
            if (!password1.equals(password2)) {
                return false
            }
            return true
        }
    }
}

object ConstantVariables {

    val apiKey = "yV1rW0bG2rQ4nD6mI0aQ5iW2dA6kH5"
}

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)