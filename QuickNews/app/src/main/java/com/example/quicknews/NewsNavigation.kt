package com.example.quicknews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.quicknews.model.Article
import com.example.quicknews.model.Graph.articleRepository
import com.example.quicknews.model.Screens
import com.example.quicknews.screens.DetailScreen
import com.example.quicknews.screens.NewsScreen


@Composable
fun NewsApp(navController: NavHostController) {
    val newsViewModel: NewsViewModel = viewModel(
        factory = ArticleViewModelFactory(articleRepository)
    )
    val viewState by newsViewModel.newsState
    val currentCategory by newsViewModel.currentCategory

    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {
        composable(Screens.MainScreen.route) {
            NewsScreen(
                viewState = viewState,
                currentCategory = currentCategory,
                onCategoryChange = { newCategory -> newsViewModel.changeCategory(newCategory) },
                onDetailClick = { article ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("new", article)
                    navController.navigate(Screens.DetailScreen.route)
                }
            )
        }
        composable(Screens.DetailScreen.route) {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("new")
            if (article != null) {
                DetailScreen(article = article)
            }
        }
    }
}