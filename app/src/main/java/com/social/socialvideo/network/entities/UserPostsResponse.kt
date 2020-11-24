package com.social.socialvideo.network.entities

import com.squareup.moshi.JsonClass



@JsonClass(generateAdapter = true)
data class UserPostsResponse (
     val postid: String,
     val created: String,
     val videourl: String,
     val username: String,
     val profile: String
)