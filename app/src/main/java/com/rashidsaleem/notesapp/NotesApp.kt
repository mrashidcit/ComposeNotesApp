package com.rashidsaleem.notesapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NotesApp: Application() {

    override fun onCreate() {
        super.onCreate()

    }


}