package com.example.quicknews.model

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [Article::class],
    version = 1,
    exportSchema = false
)

abstract class ArticleDatabase: RoomDatabase() {
    abstract fun ArticleDao(): ArticleDao
}

