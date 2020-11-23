package com.social.socialvideo.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.social.socialvideo.db.UserPostRepository
import com.social.socialvideo.db.entities.dao.getDatabase

/**
 * @param application The application that this viewmodel is attached to, it's safe to hold a
 * reference to applications across rotation since Application is never recreated during actiivty
 * or fragment lifecycle events.
 * */
class PostsViewModel(application: Application) : AndroidViewModel(application) {

    private val videosRepository = UserPostRepository(
        getDatabase(
            application
        )
    )

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