package com.example.shoppinglist.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ShoppingApi {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int = 30,
        @Query("skip") skip: Int = 0
    ): ProductsResponse
}
