/*
package com.social.socialvideo.db.entities

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.social.socialvideo.domain.UserProfile

@Entity
data class DatabaseUserProfile constructor(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String,
    val token: String,
    val refresh: String,
    val profile: String
)
fun DatabaseUserProfile.asDomainModel(): UserProfile {
    return UserProfile(
            id = id,
            username = username,
            email = email,
            token = token,
            refresh = refresh,
            profile = profile)
    }
*/
