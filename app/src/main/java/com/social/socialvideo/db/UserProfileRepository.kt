/*
package com.social.socialvideo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.social.socialvideo.db.entities.asDomainModel
import com.social.socialvideo.db.entities.dao.SocialVideoDatabase
import com.social.socialvideo.domain.UserProfile
import com.social.socialvideo.entities.UserInfoRequest
import com.social.socialvideo.entities.UserInfoResponse
import com.social.socialvideo.network.RestApiService
import com.social.socialvideo.network.retrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileRepository(private val database: SocialVideoDatabase) {

    // Data z databazy transformujeme do nasho domenoveho objektu s ktorym neskor pracujeme
    lateinit var getUserProfile: LiveData<UserProfile>

    fun getUser(username: String){
        getUserProfile = Transformations.map(database.userProfileDao.getUserProfile(username)) {
            it.asDomainModel()
        }
    }

    suspend fun addUser(token: String) {
        withContext(Dispatchers.IO) {
            val userInfoRequest = initUserInfoRequest(token)
            val apiService = retrofit.create(RestApiService::class.java)
            val loginResponse: Call<UserInfoResponse> = apiService.userInfo(userInfoRequest)

            loginResponse.enqueue(object : Callback<UserInfoResponse> {
                override fun onFailure(call: Call<UserInfoResponse>?, t: Throwable?) {
                }
                override fun onResponse(
                    call: Call<UserInfoResponse>?,
                    response: Response<UserInfoResponse>?
                ) {
                    if(response?.code() == 200) {

                    }else{

                    }
                }
            })
        }
    }

    private fun initUserInfoRequest(token: String): UserInfoRequest {
        val userInfoRequest = UserInfoRequest()
        userInfoRequest.token = token
        return userInfoRequest
    }

    suspend fun refreshVideos() {
        */
/*withContext(Dispatchers.IO) {
            val playlist = Network.devbytes.getPlaylist().await()
            database.videoDao.insertAll(*playlist.asDatabaseModel())
        }*//*

    }
}*/
