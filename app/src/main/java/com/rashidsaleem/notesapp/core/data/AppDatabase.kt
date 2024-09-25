package com.rashidsaleem.notesapp.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        private var _instance: AppDatabase? = null

        fun getInstance(ctx: Context): AppDatabase {

            if (_instance == null) {
                _instance = Room.databaseBuilder(
                    ctx,
                    AppDatabase::class.java,
                    "AppDatabase"
                ).build()
            }

            return _instance!!
        }
    }
}