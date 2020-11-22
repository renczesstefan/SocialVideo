package com.social.socialvideo.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.social.socialvideo.db.entities.DatabaseUserPost
import com.social.socialvideo.db.entities.UserPostDatabase
import com.social.socialvideo.db.entities.asDomainModel
import com.social.socialvideo.domain.UserPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserPostRepository(private val database: UserPostDatabase) {

    // Data z databazy transformujeme do nasho domenoveho objektu s ktorym neskor pracujeme
    val posts: LiveData<List<UserPost>> =
        Transformations.map(database.userPostDao.getVideos()) {
            it.asDomainModel()
        }

    suspend fun addPost() {
        withContext(Dispatchers.IO) {

            database.userPostDao.insert(DatabaseUserPost("a","b","c","d"))
            database.userPostDao.insert(DatabaseUserPost("b","qwe","qwe","qwe"))
            database.userPostDao.insert(DatabaseUserPost("c","asdsad","qweewqq","zxczxczxc"))
            database.userPostDao.insert(DatabaseUserPost("d","fsdafsda","sfdsdaf","sfdsdf"))
        }
    }


}