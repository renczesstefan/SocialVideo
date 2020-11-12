package com.social.socialvideo.entities

import com.social.socialvideo.utils.ConstantVariables

class RegistrationRequest(var email: String, var username: String, var password: String) {

    private val action: String = "user"
    private val apikey: String = ConstantVariables.apiKey

}
