package com.social.socialvideo.rest.entities;

import com.social.socialvideo.utils.ConstantVariables

class RemoveProfileRequest {
    var action: String = "clearPhoto"
    var apikey: String = ConstantVariables.API_KEY
    lateinit var token: String
}
