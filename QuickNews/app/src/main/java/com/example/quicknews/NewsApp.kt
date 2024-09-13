package com.example.quicknews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NewsApp (navController: NavHostController) {

    val newsViewModel: NewsViewModel = viewModel()
    val viewState by newsViewModel.newsState

    NavHost(navController = navController, startDestination = Screens.MainScreen.route){
        composable(Screens.MainScreen.route){
            NewsScreen(viewState = viewState, onDetailClick = {
                navController.currentBackStackEntry?.savedStateHandle?.set("new", it)
                navController.navigate(Screens.DetailScreen.route)
            })
        }
        composable(Screens.DetailScreen.route) {
            val article = navController.previousBackStackEntry?.savedStateHandle?.get<Article>("article")
            article?.let {
                DetailScreen(article = it)
            }
        }
    }
}