package com.example.quicknews

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.quicknews.model.Article
import com.example.quicknews.model.ArticleDao
import com.example.quicknews.model.ArticleRepository
import com.example.quicknews.model.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NewsViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val _newsState = mutableStateOf(NewsState())
    val newsState: State<NewsState> = _newsState

    private val _currentCategory = mutableStateOf("general")
    val currentCategory: State<String> = _currentCategory

    lateinit var getLocalNews: Flow<List<Article>>

    init {
        fetchNews(_currentCategory.value)
        getLocalNews = articleRepository.getAllArticles()
    }

    fun addArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            articleRepository.addArticle(article)
        }
    }
    fun getArticleById(id: Int): Flow<Article> {
        return articleRepository.getArticleById(id)
    }

    private fun fetchNews(query: String) {
        viewModelScope.launch {
            _newsState.value = _newsState.value.copy(loading = true, error = null)
            try {
                val response = RetrofitInstance.api.getEverything(
                    query = query,
                    apiKey = "2c39949b0fff4e74964f7b1d5d6cebcf"
                )
                response.body()?.articles?.let { articles ->
                    _newsState.value = _newsState.value.copy(
                        list = articles,
                        loading = false,
                        error = null
                    )
                } ?: run {
                    _newsState.value = _newsState.value.copy(
                        loading = false,
                        error = "No se encontraron artículos"
                    )
                }
            } catch (e: HttpException) {
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error de red: ${e.message}"
                )
            } catch (e: IOException) {
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error de conexión: ${e.message}"
                )
            } catch (e: Exception) {
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error inesperado: ${e.message}"
                )
            } finally {
                _newsState.value = _newsState.value.copy(loading = false)
            }
        }
    }

    fun changeCategory(newCategory: String) {
        _currentCategory.value = newCategory
        fetchNews(newCategory)
    }

    data class NewsState(
        val loading: Boolean = true,
        val list: List<Article> = emptyList(),
        val error: String? = null
    )
}