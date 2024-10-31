package com.example.quicknews.model

sealed class Screens(val route: String) {
    object MainScreen: Screens("mainscreen")
    object DetailScreen: Screens("detailscreen")
}