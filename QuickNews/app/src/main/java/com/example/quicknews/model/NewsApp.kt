package com.example.quicknews.model

import android.app.Application

class NewsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}