package com.example.shoppinglist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val quantity: Int = 1,
    val unit: String = "шт",
    val isPurchased: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
