package com.rashidsaleem.notesapp.feature_addNote.domain

import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNoteUseCase(
    private val repository: NotesRepository

) {
    suspend fun execute(id: Int): NoteModel {
        return withContext(Dispatchers.IO) {
            repository.get(id)
        }
    }

}