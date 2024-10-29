package com.rashidsaleem.notesapp.feature_home.domain

import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNotesUseCase(
    private val repository: NotesRepository
) {

    suspend fun execute(): List<NoteModel> {

        return withContext(Dispatchers.IO) {
            repository.getAll()
        }

    }
}