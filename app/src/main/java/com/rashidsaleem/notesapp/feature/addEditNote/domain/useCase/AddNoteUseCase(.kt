package com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase

import com.rashidsaleem.notesapp.core.domain.model.Note
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddNoteUseCase(
    private val repository: NotesRepository,
) {

    suspend fun execute(note: Note) {
        return withContext(Dispatchers.IO) {
            if (note.title.isBlank() && note.description.isBlank()) return@withContext

            repository.addNote(note)
        }
    }

}