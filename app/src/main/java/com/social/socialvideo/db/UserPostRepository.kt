package com.social.socialvideo.db

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.social.socialvideo.db.dao.SocialVideoDatabase
import com.social.socialvideo.db.entities.DatabaseUserPost
import com.social.socialvideo.db.entities.asDomainModel
import com.social.socialvideo.domain.UserPost
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.entities.UserPostsRequest
import com.social.socialvideo.network.entities.UserPostsResponse
import com.social.socialvideo.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPostRepository(private val database: SocialVideoDatabase) {

    // Data z databazy transformujeme do nasho domenoveho objektu s ktorym neskor pracujeme
    val posts: LiveData<List<UserPost>> =
        Transformations.map(database.userPostsDao.getVideos()) {
            it.asDomainModel()
        }

    suspend fun insertUserPosts(userPostsRequest: UserPostsRequest) {
        withContext(Dispatchers.IO) {
            val apiService = retrofit.create(RestApiService::class.java)
            val userPosts = apiService.userPosts(userPostsRequest).await()
            val databaseUserPosts = asDatabaseModel(userPosts)
            database.userPostsDao.insertAll(*databaseUserPosts)
        }
    }


    /*suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }
    }*/

    fun asDatabaseModel(userPosts: List<UserPostsResponse>): Array<DatabaseUserPost> {
        return userPosts.map {
            DatabaseUserPost(
                postid = it.postid,
                created = it.created,
                profile = it.profile,
                username = it.username,
                videourl = it.videourl
            )
        }.toTypedArray()
    }

}