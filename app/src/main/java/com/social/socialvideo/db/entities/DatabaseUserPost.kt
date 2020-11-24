package com.social.socialvideo.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.social.socialvideo.domain.UserPost

@Entity
data class DatabaseUserPost constructor(
    @PrimaryKey
    var postid: String,
    var created: String,
    var videourl: String,
    var username: String,
    var profile: String
)
fun List<DatabaseUserPost>.asDomainModel(): List<UserPost> {
    return map {
        UserPost(
            postid = it.postid,
            created = it.created,
            videourl = it.videourl,
            username = it.username,
            profile = it.profile)
    }
}

