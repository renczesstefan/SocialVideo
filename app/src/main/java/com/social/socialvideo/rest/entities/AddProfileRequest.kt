package com.social.socialvideo.rest.entities

import com.social.socialvideo.utils.ConstantVariables

class AddProfileRequest {
    val apikey  = ConstantVariables.API_KEY
    lateinit var token: String
}