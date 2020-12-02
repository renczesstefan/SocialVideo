package com.social.socialvideo.db

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.social.socialvideo.db.dao.SocialVideoDatabase
import com.social.socialvideo.db.entities.DatabaseUserPost
import com.social.socialvideo.db.entities.asDomainModel
import com.social.socialvideo.domain.UserPost
import com.social.socialvideo.rest.services.RestApiService
import com.social.socialvideo.rest.entities.UserPostsRequest
import com.social.socialvideo.rest.entities.UserPostsResponse
import com.social.socialvideo.rest.services.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPostRepository(private val database: SocialVideoDatabase) {
    /**
    * Data z databazy transformujeme do nasho domenoveho objektu s ktorym neskor pracujeme
    */
    val posts: LiveData<List<UserPost>> =
        Transformations.map(database.userPostsDao.getVideos()) {
            it.asDomainModel()
        }

    suspend fun insertUserPosts(userPostsRequest: UserPostsRequest, context: Context) {
        withContext(Dispatchers.IO) {
            val apiService = retrofit.create(
                RestApiService::class.java)
            try {
                val userPosts = apiService.userPosts(userPostsRequest).await()
                val databaseUserPosts = asDatabaseModel(userPosts)
                database.userPostsDao.insertAll(*databaseUserPosts)
            } catch (e: Exception) {
            }

        }
    }

    /**
     * Objekt ziskany z api requestu na posty pretransformujeme(namapujeme) na databazove objekty, ktore budeme neskor ukladat do databazy.
     */
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