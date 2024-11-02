package com.example.quicknews.model

import android.content.Context
import androidx.room.Room

object Graph {
    lateinit var database: ArticleDatabase
        private set

    val articleRepository by lazy {
        ArticleRepository(database.ArticleDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_database"
        ).build()
    }
}