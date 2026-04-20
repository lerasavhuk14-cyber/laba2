package com.example.shoppinglist.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM shopping_items ORDER BY isPurchased ASC, createdAt DESC")
    fun getAllItems(): Flow<List<ShoppingItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ShoppingItem)

    @Update
    suspend fun updateItem(item: ShoppingItem)

    @Delete
    suspend fun deleteItem(item: ShoppingItem)

    @Query("SELECT COUNT(*) FROM shopping_items WHERE isPurchased = 1")
    fun getPurchasedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM shopping_items")
    fun getTotalCount(): Flow<Int>
}
