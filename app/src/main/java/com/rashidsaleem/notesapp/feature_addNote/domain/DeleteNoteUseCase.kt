package com.rashidsaleem.notesapp.feature_addNote.domain

import com.rashidsaleem.notesapp.feature_core.data.respository.NotesRepository

class DeleteNoteUseCase {

    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(id: Int) {

        repository.delete(id)
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