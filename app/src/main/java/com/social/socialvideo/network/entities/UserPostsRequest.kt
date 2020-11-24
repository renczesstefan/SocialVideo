package com.social.socialvideo.network.entities

import com.social.socialvideo.utils.ConstantVariables
import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
class UserPostsRequest {
    var action = ConstantVariables.USER_POSTS
    var apikey =  ConstantVariables.API_KEY
    lateinit var token: String
}