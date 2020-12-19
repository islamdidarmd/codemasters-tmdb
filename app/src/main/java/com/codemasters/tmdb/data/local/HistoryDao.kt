package com.codemasters.tmdb.data.local

import androidx.room.*
import com.codemasters.tmdb.data.model.ContentDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Query("select * from wishlist")
    fun getAll(): Flow<List<ContentDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(content: ContentDetails)

    @Query("delete from wishlist where id = :id")
    fun delete(id: Int)
}