package com.nikendo.app.todolist

import android.app.Application
import com.nikendo.app.todolist.repository.AppDatabase

class App : Application() {
    val database by lazy { AppDatabase.createDatabase(this) }
}