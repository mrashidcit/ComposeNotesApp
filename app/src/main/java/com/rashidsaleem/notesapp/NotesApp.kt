package com.rashidsaleem.notesapp

import android.app.Application
import com.rashidsaleem.notesapp.core.data.local.AppDatabase
import com.rashidsaleem.notesapp.core.data.repository.NotesRepositoryImpl

class NotesApp: Application() {

    val database by lazy { AppDatabase.getInstance(this) }

    val repository by lazy { NotesRepositoryImpl(database) }

}