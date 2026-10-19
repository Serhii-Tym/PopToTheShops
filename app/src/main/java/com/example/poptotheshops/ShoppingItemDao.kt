package com.example.poptotheshops

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM ShoppingItem")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Insert
    suspend fun insertItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)
}