package com.rashidsaleem.notesapp.feature_home.domain.useCases

import com.rashidsaleem.notesapp.core.domain.models.Resource
import com.rashidsaleem.notesapp.feature_home.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UpdateNoteUseCase constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(note: NoteModel): Resource<String> {

        // Save Note
        val isNoteEmpty = note.let {
            it.title.isEmpty() && it.description.isEmpty()
        }
        if (isNoteEmpty) {

            return Resource.Error("Please enter Title or Description")
        }

        repository.update(note)
        return Resource.Success("Successfully Updated")
    }
}