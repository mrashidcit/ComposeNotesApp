package com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase

import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNoteUseCase(
    private val notesRepository: NotesRepository,
) {

    suspend fun execute(noteId: Int) {
        return withContext(Dispatchers.IO) {

            if (noteId == -1) return@withContext

            // delete not from db
            notesRepository.delete(noteId)
        }
    }
}