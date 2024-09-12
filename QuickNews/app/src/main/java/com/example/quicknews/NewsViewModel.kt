package com.example.quicknews

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class NewsViewModel : ViewModel() {

    private val _newsState = mutableStateOf(NewsState())
    val newsState: State<NewsState> = _newsState

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            _newsState.value = _newsState.value.copy(loading = true, error = null)
            try {
                val response = NewsService.getNews(country = "us", category = null)
                println("Fetched News: ${response.data}")
                _newsState.value = response.data?.let {
                    _newsState.value.copy(
                        list = it,
                        loading = false,
                        error = null
                    )
                }!!
            } catch (e: HttpException) {
                println("HTTP error fetching news: ${e.message}")
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error de red: ${e.message}"
                )
            } catch (e: IOException) {
                println("IO error fetching news: ${e.message}")
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error de conexión: ${e.message}"
                )
            } catch (e: Exception) {
                println("Error fetching news: ${e.message}")
                _newsState.value = _newsState.value.copy(
                    loading = false,
                    error = "Error inesperado: ${e.message}"
                )
            }
        }
    }

    data class NewsState(
        val loading: Boolean = true,
        val list: List<News> = emptyList(),
        val error: String? = null
    )
}