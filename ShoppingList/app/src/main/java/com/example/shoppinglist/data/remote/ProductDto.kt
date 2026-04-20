package com.example.shoppinglist.data.remote

data class ProductsResponse(
    val products: List<ProductDto>,
    val total: Int,
    val skip: Int,
    val limit: Int
)

data class ProductDto(
    val id: Int,
    val title: String,
    val category: String,
    val price: Double,
    val thumbnail: String
)
