package com.rashidsaleem.notesapp.feature_home.domain

import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListenNotesUseCase {

    private val repository: NotesRepositoryImpl = NotesRepositoryImpl.getInstance()

    suspend fun execute(): Flow<NotesEvent> {

        return channelFlow {

            withContext(Dispatchers.IO) {

                launch {
                    repository.newNoteInsertionListener.collect { newNote ->
                        send(NotesEvent.Insert(newNote))
                    }
                }

                launch {
                    repository.updateNoteListener.collect { updatedNote ->
                        send(NotesEvent.Update(updatedNote))
                    }
                }

                launch {
                    repository.deleteNoteListener.collect { id ->
                        send(NotesEvent.Delete(id))
                    }
                }

            }
        }
    }

    companion object {
        private var _instance: ListenNotesUseCase? = null

        fun getInstance(): ListenNotesUseCase {
            if (_instance == null) {
                _instance = ListenNotesUseCase()
            }

            return _instance!!
        }
    }

}


sealed interface NotesEvent {
    data class Insert(val value: NoteModel) : NotesEvent
    data class Update(val value: NoteModel) : NotesEvent
    data class Delete(val value: Int) : NotesEvent
}