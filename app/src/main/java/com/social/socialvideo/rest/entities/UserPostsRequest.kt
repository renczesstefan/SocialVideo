package com.social.socialvideo.rest.entities

import com.social.socialvideo.utils.ConstantVariables

class UserPostsRequest {
    var action = ConstantVariables.USER_POSTS
    var apikey =  ConstantVariables.API_KEY
    lateinit var token: String
}