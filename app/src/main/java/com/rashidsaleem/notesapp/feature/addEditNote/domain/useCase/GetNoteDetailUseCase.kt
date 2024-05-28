package com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase

import com.rashidsaleem.notesapp.feature.home.domain.models.Note
import com.rashidsaleem.notesapp.feature.home.domain.models.dummyNotes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNoteDetailUseCase {

    suspend fun execute(noteId: Int): Note? {
        return withContext(Dispatchers.IO) {

            if (noteId == -1) return@withContext null

            dummyNotes().firstOrNull { it.id == noteId }
        }
    }

}