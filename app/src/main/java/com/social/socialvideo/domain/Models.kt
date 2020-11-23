package com.social.socialvideo.domain

data class UserPost(
    val postid: String,
    val created: String,
    val videourl: String,
    val username: String,
    val profile: String)

data class UserProfile(
    val id: String,
    val username: String,
    val email: String,
    val token: String,
    val refresh: String,
    val profile: String)