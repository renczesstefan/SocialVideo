package com.social.socialvideo.db

import androidx.lifecycle.LiveData
import com.social.socialvideo.db.entities.UserPost
import com.social.socialvideo.db.entities.UserPostDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPostRepository(private val database: UserPostDatabase) {

    suspend fun addPost() {
        withContext(Dispatchers.IO) {
            database.userPostDao.insert(UserPost("a","b","c","d"))
        }
    }
    suspend fun getPosts() {
        withContext(Dispatchers.IO) {
            database.userPostDao.getVideos()
        }
    }

}