package com.urlshotener.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface URLItemDao {
    @Query("SELECT * from URLItem")
    fun getAll(): Flow<List<URLItem>>

    @Query("SELECT * from URLItem WHERE id=:id")
    fun getItem(id: Int): Flow<URLItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(urlItem: URLItem)

    @Update
    suspend fun update(urlItem: URLItem)

    @Delete
    suspend fun delete(urlItem: URLItem)

    @Query("DELETE from URLItem WHERE id=:id")
    suspend fun deleteById(id: Int)

//    @Delete
//    suspend fun delete(int: Int)

//    @Query("SELECT * from URLItem ORDER BY name ASC")
//    fun getItems(): Flow<List<URLItem>>
}