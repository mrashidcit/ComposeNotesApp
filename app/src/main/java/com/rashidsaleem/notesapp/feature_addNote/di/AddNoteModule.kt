package com.rashidsaleem.notesapp.feature_addNote.di

import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_addNote.domain.AddNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.DeleteNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.GetNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object AddNoteModule {

    @Provides
    fun provideGetNoteUseCase(repository: NotesRepository) : GetNoteUseCase {
        return GetNoteUseCase(repository)
    }

    @Provides
    fun provideDeleteNoteUseCase(repository: NotesRepository): DeleteNoteUseCase {
        return DeleteNoteUseCase(repository)
    }
}