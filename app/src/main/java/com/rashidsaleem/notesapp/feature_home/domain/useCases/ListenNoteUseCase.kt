package com.rashidsaleem.notesapp.feature_home.domain.useCases

import com.rashidsaleem.notesapp.feature_home.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.models.NoteModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListenNoteUseCase(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(): Flow<NoteEvents> {

        return flow {

            withContext(ioDispatcher) {

                launch {
                    repository.insertionListener.collect { item ->
                        emit(NoteEvents.Insertion(item))
                    }
                }

                launch {
                    repository.updateListener.collect { item ->
                        emit(NoteEvents.Updation(item))
                    }
                }

                launch {
                    repository.deleteListener.collect { id ->
                        emit(NoteEvents.Deletion(id))
                    }
                }

            }
        }

    }
}


sealed interface NoteEvents {
    data class Insertion(val value: NoteModel): NoteEvents
    data class Updation(val value: NoteModel): NoteEvents
    data class Deletion(val id: Int): NoteEvents
}