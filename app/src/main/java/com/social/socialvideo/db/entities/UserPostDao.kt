package com.social.socialvideo.db.entities

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserPostDao {
    @Query("select * from userpost")
    fun getVideos(): LiveData<List<UserPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( videos: UserPost)
}

@Database(entities = [UserPost::class], version = 1)
abstract class UserPostDatabase: RoomDatabase() {
    abstract val userPostDao: UserPostDao
}

private lateinit var INSTANCE: UserPostDatabase

fun getDatabase(context: Context): UserPostDatabase {
    synchronized(UserPostDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                UserPostDatabase::class.java,
                "videos").build()
        }
    }
    return INSTANCE
}