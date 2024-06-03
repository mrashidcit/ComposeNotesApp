package com.rashidsaleem.notesapp

import android.app.Application
import com.rashidsaleem.notesapp.core.data.local.AppDatabase
import com.rashidsaleem.notesapp.core.data.repository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository

class NotesApp: Application() {



    override fun onCreate() {
        super.onCreate()

        database = AppDatabase.getInstance(this)
        repository = NotesRepositoryImpl(database)
    }

    companion object {
        lateinit var database: AppDatabase

        lateinit var repository: NotesRepository
    }

}