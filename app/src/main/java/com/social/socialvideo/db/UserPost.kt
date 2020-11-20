package com.social.socialvideo.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
data class UserPost constructor(
    @PrimaryKey
    val userName: String,
    val profilePhoto : String,
    val name: String,
    val surname: String,
)
