package com.social.socialvideo.rest.entities

data class UserPostsResponse (
     val postid: String,
     val created: String,
     val videourl: String,
     val username: String,
     val profile: String
)