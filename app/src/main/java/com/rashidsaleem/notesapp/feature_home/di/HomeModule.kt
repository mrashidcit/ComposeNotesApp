package com.rashidsaleem.notesapp.feature_home.di

import android.content.Context
import androidx.room.Room
import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.core.data.local.AppDatabase
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.GetNotesUseCase
import com.rashidsaleem.notesapp.feature_home.domain.ListenNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {

    @Provides
    fun provideGetNotesUseCase(repository: NotesRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }

    @Provides
    fun provideListenNotesUseCase(repository: NotesRepository): ListenNotesUseCase {
        return ListenNotesUseCase(repository)
    }

}