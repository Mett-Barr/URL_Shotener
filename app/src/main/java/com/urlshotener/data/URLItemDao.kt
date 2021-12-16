package com.urlshotener.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface URLItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(urlItem: URLItem)

    @Update
    suspend fun update(urlItem: URLItem)

    @Delete
    suspend fun delete(urlItem: URLItem)

    @Query("SELECT * from URLItem WHERE id = :id")
    fun getItem(id: Int): Flow<URLItem>

//    @Query("SELECT * from URLItem ORDER BY name ASC")
//    fun getItems(): Flow<List<URLItem>>
}