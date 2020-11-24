package com.social.socialvideo.network.entities

import com.social.socialvideo.utils.ConstantVariables

class AddProfileRequest {
    val apikey  = ConstantVariables.API_KEY
    lateinit var token: String
}