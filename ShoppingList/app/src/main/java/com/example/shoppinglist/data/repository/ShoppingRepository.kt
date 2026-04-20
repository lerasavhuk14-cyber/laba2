package com.example.shoppinglist.data.repository

import com.example.shoppinglist.data.local.ShoppingDao
import com.example.shoppinglist.data.local.ShoppingItem
import com.example.shoppinglist.data.remote.ShoppingApi
import kotlinx.coroutines.flow.Flow

class ShoppingRepository(
    private val dao: ShoppingDao,
    private val api: ShoppingApi
) {
    val allItems: Flow<List<ShoppingItem>> = dao.getAllItems()
    val purchasedCount: Flow<Int> = dao.getPurchasedCount()
    val totalCount: Flow<Int> = dao.getTotalCount()

    suspend fun addItem(item: ShoppingItem) = dao.insertItem(item)
    suspend fun updateItem(item: ShoppingItem) = dao.updateItem(item)
    suspend fun deleteItem(item: ShoppingItem) = dao.deleteItem(item)

    suspend fun fetchSuggestions(): Result<List<String>> {
        return try {
            val skip = (0..5).random() * 10
            val response = api.getProducts(limit = 20, skip = skip)
            Result.success(response.products.map { it.title })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
