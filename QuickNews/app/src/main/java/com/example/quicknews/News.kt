package com.example.quicknews

data class News(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val source: String?,
    val image: String?,
    val category: String?,
    val language: String?,
    val country: String?,
    val published_at: String?
)

data class NewsResponse(
    val data: List<News>?,
    val pagination: Pagination?
)

data class Pagination(
    val limit: Int,
    val offset: Int,
    val count: Int,
    val total: Int
)