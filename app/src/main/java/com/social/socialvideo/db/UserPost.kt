package com.social.socialvideo.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.social.socialvideo.domain.UserPost

@Entity
data class DatabaseUserPost constructor(
    @PrimaryKey
    val userName: String,
    val profilePhoto : String,
    val name: String,
    val surname: String,
)
fun List<DatabaseUserPost>.asDomainModel(): List<UserPost> {
    return map {
        UserPost(
            userName = it.userName,
            profilePhoto = it.profilePhoto,
            name = it.name,
            surname = it.surname)
    }
}

