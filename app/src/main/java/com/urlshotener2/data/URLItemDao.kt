package com.urlshotener2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface URLItemDao {

//    @Query("SELECT * from URLItem ORDER BY id DESC")
//    fun getAll(): Flow<List<URLItem>>

    @Query("SELECT * from URLItem WHERE deleted=:deleteState ORDER BY id DESC")
    fun getAllByDeleteState(deleteState: Int): Flow<List<URLItem>>

    @Query("SELECT COUNT(*) from URLItem")
    fun getSize(): Flow<Int>

    @Query("SELECT * from URLItem WHERE id=:id")
    fun getItem(id: Int): Flow<URLItem>

    @Query("SELECT * from URLItem WHERE `origin URL`=:url")
    fun getItemByURL(url: String): Flow<URLItem>

    @Query("SELECT COUNT(`origin URL`) from URLItem WHERE `origin URL`=:url")
    suspend fun existed(url: String): Int

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