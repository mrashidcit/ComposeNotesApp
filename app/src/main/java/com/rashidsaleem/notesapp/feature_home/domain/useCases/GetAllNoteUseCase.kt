package com.rashidsaleem.notesapp.feature_home.domain.useCases

import com.rashidsaleem.notesapp.feature_home.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAllNoteUseCase constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(): List<NoteModel> {

        return withContext(ioDispatcher) {
            repository.getAll()
        }
    }
}