package com.social.socialvideo.db.dao

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.social.socialvideo.db.entities.DatabaseUserPost

@Dao
interface UserPostsDao {
    @Query("select * from databaseuserpost")
    fun getVideos(): LiveData<List<DatabaseUserPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseUserPost)
}

@Database(entities = [DatabaseUserPost::class], version = 1)
abstract class SocialVideoDatabase: RoomDatabase() {
    abstract val userPostsDao: UserPostsDao
}

private lateinit var INSTANCE: SocialVideoDatabase

/**
 * Funckia vytvorena podla kurzu. Funkcia zabezpecuje vratenie iba jednej instancie medzi vsetkymi vlaknami (signleton)
 * */
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