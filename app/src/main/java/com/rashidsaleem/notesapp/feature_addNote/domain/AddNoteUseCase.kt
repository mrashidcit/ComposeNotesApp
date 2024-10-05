package com.rashidsaleem.notesapp.feature_addNote.domain

import com.rashidsaleem.notesapp.feature_core.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel

class AddNoteUseCase  {

    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(noteModel: NoteModel) {

        if (noteModel.id == -1) {
            repository.insert(noteModel)
        } else {
            repository.update(noteModel)
        }
    }

    companion object {
        private var _instance: AddNoteUseCase? = null
        fun getInstance() : AddNoteUseCase {
            if (_instance == null) {
                _instance = AddNoteUseCase()
            }

            return _instance!!
        }
    }
}