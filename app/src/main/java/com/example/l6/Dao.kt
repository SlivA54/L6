package com.example.l6

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {
    @Insert
    suspend fun insertItem(item: Item)

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>
    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Item?
    @Delete
    suspend fun deleteItem(item: Item)

}