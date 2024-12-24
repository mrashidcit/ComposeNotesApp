package com.rashidsaleem.notesapp.feature_home.di

import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.GetNotesUseCase
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object HomeModule {



    @Provides
    fun provideGetNotesUseCase(repository: NotesRepository): GetNotesUseCase {
        return GetNotesUseCase(repository)
    }




}