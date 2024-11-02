package com.example.quicknews.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
abstract class ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertArticle(article: Article)

    @Query("SELECT * FROM `articles-table`")
    abstract fun getAllArticles(): Flow<List<Article>>

    @Query("SELECT * FROM `articles-table` where articleId = :articleId")
    abstract fun getArticleById(articleId: Int): Flow<Article>

}