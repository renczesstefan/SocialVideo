package com.social.socialvideo.rest.entities;

class ChangePasswordRequest {
    var action: String = "password"
    var apikey: String = "yV1rW0bG2rQ4nD6mI0aQ5iW2dA6kH5"
    lateinit var token: String
    lateinit var oldpassword: String
    lateinit var newpassword: String
}
