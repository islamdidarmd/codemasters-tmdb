package com.codemasters.tmdb.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.codemasters.tmdb.data.model.ContentDetails

@Database(entities = [ContentDetails::class], version = 1, exportSchema = true)
abstract class ContentDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var mInstance: ContentDatabase? = null

        fun getSpeedDialDatabase(applicationContext: Context): ContentDatabase {
            if (mInstance == null) {
                mInstance = getDB(applicationContext, "tmdb.db")
            }
            return mInstance!!
        }
    }

    abstract fun getDao(): ContentDao
}

private inline fun <reified T : RoomDatabase> getDB(applicationContext: Context, name: String): T {
    return Room.databaseBuilder(
        applicationContext.applicationContext,
        T::class.java,
        name
    ).fallbackToDestructiveMigration().build()
}