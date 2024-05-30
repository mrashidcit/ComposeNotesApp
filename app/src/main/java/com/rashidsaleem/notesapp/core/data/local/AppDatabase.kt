package com.rashidsaleem.notesapp.core.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rashidsaleem.notesapp.core.data.local.dao.NoteDao
import com.rashidsaleem.notesapp.core.data.local.entity.NoteEntity
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [NoteEntity::class],
    version = 1,
)
abstract class AppDatabase: RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(
                context: Context,
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database",
                    )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }

        }
    }


    abstract fun noteDao(): NoteDao
}