package com.rashidsaleem.notesapp.feature.home.domain.useCase

import com.rashidsaleem.notesapp.core.domain.model.Note
import com.rashidsaleem.notesapp.core.domain.model.dummyNotes
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(
    private val repository: NotesRepository
) {

    suspend fun execute(): Flow<List<Note>> {
        return repository
            .getNotes()
    }

}