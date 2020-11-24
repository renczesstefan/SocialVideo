package com.social.socialvideo.network.entities;

import com.social.socialvideo.utils.ConstantVariables

class UserInfoRequest {
    var action: String = ConstantVariables.USER_PROFILE
    var apikey: String = ConstantVariables.API_KEY
    lateinit var token: String
}
