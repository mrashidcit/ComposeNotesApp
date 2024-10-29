package com.rashidsaleem.notesapp.core.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao
}