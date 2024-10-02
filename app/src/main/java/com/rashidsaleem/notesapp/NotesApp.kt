package com.rashidsaleem.notesapp

import android.app.Application
import android.content.Context

class NotesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = applicationContext
    }

    companion object {

        lateinit var appContext: Context
    }

}