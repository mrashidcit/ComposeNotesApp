package com.rashidsaleem.notesapp.feature_addNote.domain

import com.rashidsaleem.notesapp.feature_core.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel

class GetNoteUseCase {

    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(id: Int): NoteModel {
        return repository.get(id)
    }

    companion object {
        private var _instance: GetNoteUseCase? = null
        fun getInstance(): GetNoteUseCase {
            if (_instance == null) {
                _instance = GetNoteUseCase()
            }

            return _instance!!
        }
    }
}