package com.example.quicknews.model

import kotlinx.coroutines.flow.Flow

class ArticleRepository(private val articleDao: ArticleDao) {

    suspend fun addArticle(article: Article){
        articleDao.insertArticle(article)
    }

    fun getAllArticles(): Flow<List<Article>> {
        return articleDao.getAllArticles()
    }

    fun getArticleById(id: Int): Flow<Article> {
        return articleDao.getArticleById(id)
    }

}