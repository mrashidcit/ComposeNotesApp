package com.rashidsaleem.notesapp.core.domain

import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DeleteAllNotesUseCase(
    private val repository: NotesRepository,
) {

    suspend fun execute() {
        repository.deleteAll()
    }
}