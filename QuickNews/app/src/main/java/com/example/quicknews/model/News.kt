package com.example.quicknews.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

@Entity(tableName = "articles-table")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true) val articleId: Int = 0,
    @Embedded(prefix = "source_") val source: Source,
    @ColumnInfo("Article-author")
    val author: String?,
    @ColumnInfo("Article-title")
    val title: String,
    @ColumnInfo("Article-description")
    val description: String?,
    @ColumnInfo("Article-url")
    val url: String,
    @ColumnInfo("Article-urlToImage")
    val urlToImage: String?,
    @ColumnInfo("Article-publishedAt")
    val publishedAt: String,
    @ColumnInfo("Article-content")
    val content: String?,

):Parcelable

@Parcelize
data class Source(
    @ColumnInfo(name = "source_id") val id: String?,
    val name: String
):Parcelable
