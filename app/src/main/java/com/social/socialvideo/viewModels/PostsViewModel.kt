package com.social.socialvideo.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.social.socialvideo.db.UserPostRepository
import com.social.socialvideo.db.dao.getDatabase
import com.social.socialvideo.network.entities.UserPostsRequest
import com.social.socialvideo.services.SessionManager
import kotlinx.coroutines.launch

/**
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 * */
class PostsViewModel(application: Application) : AndroidViewModel(application) {

    private val videosRepository = UserPostRepository(
        getDatabase(application)
    )

    init {
        viewModelScope.launch {
            val sessionManager = SessionManager(application.applicationContext)
            val userPostRequest = UserPostsRequest()
            userPostRequest.token = sessionManager.fetchAuthToken().toString()
            videosRepository.insertUserPosts(userPostRequest)
        }
    }

    // Sluzi na zabezpecenie poslania application contextu z fragmentu do viewModelu
    // application potrebujeme na ziskanie repozitaru ktorz pracuje s databazou
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PostsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

    val postList = videosRepository.posts

}