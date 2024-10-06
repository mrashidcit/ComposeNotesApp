package com.rashidsaleem.notesapp.feature_home.domain

import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetNotesUseCase {

    private val repository: NotesRepositoryImpl = NotesRepositoryImpl.getInstance()

    suspend fun execute(): List<NoteModel> {

        return withContext(Dispatchers.IO) {
            repository.getAll().map {
                it.toModel()
            }
        }

    }

    companion object {
        private var _instance: GetNotesUseCase? = null

        fun getInstance(): GetNotesUseCase {
            if (_instance == null) {
                _instance = GetNotesUseCase()
            }

            return _instance!!
        }

    }

}