package com.rashidsaleem.notesapp.feature_home.domain

import androidx.collection.emptyIntObjectMap
import com.rashidsaleem.notesapp.feature_core.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NotesListenerUseCase {


    private val repository: NotesRepository = NotesRepository.getInstance()

    suspend fun execute(): Flow<NoteEvents> {

        return channelFlow<NoteEvents> {



            withContext(Dispatchers.IO) {

                launch {
                    repository.newNoteInsertionListener.collect { newItem ->
                        channel.send(NoteEvents.Insertion(newItem))
                    }
                }

                launch {
                    repository.updateNoteInsertionListener.collect { updatedItem ->
                        emit(NoteEvents.Update(updatedItem))
                    }
                }
//
//                launch {
//                    repository.deleteNoteListener.collect { id ->
//                        emit(NoteEvents.Delete(id))
//                    }
//                }

            }
        }

    }
}


sealed interface NoteEvents {
    data class Insertion(val value: NoteModel) : NoteEvents
    data class Update(val value: NoteModel) : NoteEvents
    data class Delete(val value: Int) : NoteEvents
}


