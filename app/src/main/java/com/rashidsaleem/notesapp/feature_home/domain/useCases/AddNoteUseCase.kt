package com.rashidsaleem.notesapp.feature_home.domain.useCases

import com.rashidsaleem.notesapp.core.domain.models.Resource
import com.rashidsaleem.notesapp.feature_home.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AddNoteUseCase constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(note: NoteModel): Resource<Int> {

        // Save Note
        val isNoteEmpty = note.let {
            it.title.isEmpty() && it.description.isEmpty()
        }
        if (isNoteEmpty) {

            return Resource.Error("Please enter Title or Description")
        }

        val id = repository.insert(note)
        return Resource.Success(id)
    }
}