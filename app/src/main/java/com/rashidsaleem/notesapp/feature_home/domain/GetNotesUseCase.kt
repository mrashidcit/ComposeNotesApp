package com.rashidsaleem.notesapp.feature_home.domain

import android.util.Log
import com.rashidsaleem.notesapp.feature_core.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel

class GetNotesUseCase {

    private val TAG = "GetNotesUseCase"
    constructor() {
        Log.d(TAG, "GetNotesUseCase - constructor: ")
    }

    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(): List<NoteModel> {
        return repository.getAll()
    }
}