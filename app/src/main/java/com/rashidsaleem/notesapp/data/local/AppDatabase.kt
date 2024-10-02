package com.rashidsaleem.notesapp.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {

        private var _instance: AppDatabase? = null

        fun getInstance(): AppDatabase {

            if (_instance == null) {
                _instance = Room.databaseBuilder(
                    NotesApp.appContext,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build()
            }

            return _instance!!
        }


    }

}