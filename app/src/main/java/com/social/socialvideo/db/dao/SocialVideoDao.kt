package com.social.socialvideo.db.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.social.socialvideo.db.entities.DatabaseUserPost
/*
import com.social.socialvideo.db.entities.DatabaseUserProfile
*/

@Dao
interface UserPostsDao {
    @Query("select * from databaseuserpost")
    fun getVideos(): LiveData<List<DatabaseUserPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseUserPost)


}

/*
@Dao
interface UserProfileDao {
    @Query("select * from databaseuserprofile where username= :username")
    fun getUserProfile(username: String): LiveData<DatabaseUserProfile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: DatabaseUserProfile)
}
*/

@Database(entities = [DatabaseUserPost::class], version = 1)
abstract class SocialVideoDatabase: RoomDatabase() {
    abstract val userPostsDao: UserPostsDao
/*
    abstract val userProfileDao: UserProfileDao
*/
}

private lateinit var INSTANCE: SocialVideoDatabase

fun getDatabase(context: Context): SocialVideoDatabase {
    synchronized(SocialVideoDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SocialVideoDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}