package com.social.socialvideo.rest.entities

import com.social.socialvideo.utils.ConstantVariables

class UploadFileRequest {
    val apikey  = ConstantVariables.API_KEY
    lateinit var token: String
}