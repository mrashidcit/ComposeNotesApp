package com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNoteUseCase {

    suspend fun execute(noteId: Int) {
        return withContext(Dispatchers.IO) {

            if (noteId == -1) return@withContext

            // delete not from db
        }
    }
}