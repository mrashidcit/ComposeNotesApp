package com.rashidsaleem.notesapp.feature_addNote.domain

import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeleteNoteUseCase {

    private val repository: NotesRepositoryImpl = NotesRepositoryImpl.getInstance()

    suspend fun execute(id: Int) {

        withContext(Dispatchers.IO) {
            repository.delete(id)
        }
    }

    companion object {
        private var _instance: DeleteNoteUseCase? = null

        fun getInstance(): DeleteNoteUseCase {
            if (_instance == null) {
                _instance = DeleteNoteUseCase()
            }

            return _instance!!
        }
    }

}