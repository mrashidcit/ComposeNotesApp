package com.rashidsaleem.notesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        private var _instance: AppDatabase? = null;

        fun getInstance(context: Context) : AppDatabase {
            if (_instance == null) {
                _instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "AppDatabase"
                )
                    .build()
            }

            return _instance!!

        }
    }


    abstract fun noteDao(): NoteDao
}