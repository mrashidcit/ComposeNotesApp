package com.rashidsaleem.notesapp.core.di

import android.content.Context
import androidx.room.Room
import com.rashidsaleem.notesapp.core.data.local.AppDatabase
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "AppDatabase"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(appDatabase: AppDatabase): NotesRepository {
        return NotesRepositoryImpl(appDatabase.notesDao())
    }
}