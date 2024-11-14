package com.example.l6

import android.app.Application
import android.content.Context
import androidx.room.Room

class App : Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "app-db")
            .build()
    }
}