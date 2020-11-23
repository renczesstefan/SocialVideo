package com.social.socialvideo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.social.socialvideo.db.entities.asDomainModel
import com.social.socialvideo.db.entities.dao.SocialVideoDatabase
import com.social.socialvideo.domain.UserPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPostRepository(private val database: SocialVideoDatabase) {

    // Data z databazy transformujeme do nasho domenoveho objektu s ktorym neskor pracujeme
    val posts: LiveData<List<UserPost>> =
        Transformations.map(database.userPostsDao.getVideos()) {
            it.asDomainModel()
        }

    suspend fun addUserPosts() {
        withContext(Dispatchers.IO) {
            // TODO
        }
    }

    /*suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }*/


}