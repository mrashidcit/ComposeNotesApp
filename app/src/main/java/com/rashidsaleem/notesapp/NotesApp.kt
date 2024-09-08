package com.rashidsaleem.notesapp

import android.app.Application
import com.rashidsaleem.notesapp.data.local.AppDatabase

class NotesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        appDatabase = AppDatabase.getInstance(this)

    }

    companion object {
        lateinit var appDatabase: AppDatabase
    }
}