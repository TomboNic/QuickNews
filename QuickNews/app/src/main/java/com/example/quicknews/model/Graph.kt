package com.example.quicknews.model

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: ArticleDatabase

    val articleRepository by lazy {
        ArticleRepository(database.ArtcleDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(context, ArticleDatabase::class.java, "articles.db").build()
    }
}