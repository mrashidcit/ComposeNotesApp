package com.rashidsaleem.notesapp.feature_home.presentation.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.feature_home.presentation.HomeRoutes
import com.rashidsaleem.notesapp.feature_home.domain.models.NoteModel
import com.rashidsaleem.notesapp.feature_home.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.useCases.GetAllNoteUseCase
import com.rashidsaleem.notesapp.feature_home.domain.useCases.ListenNoteUseCase
import com.rashidsaleem.notesapp.feature_home.domain.useCases.NoteEvents
import com.rashidsaleem.notesapp.feature_home.domain.useCases.NoteUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val TAG = "HomeViewModel"

//    private val repository: NotesRepository = NotesRepository.getInstance()
    val noteUseCases: NoteUseCases = NoteUseCases.getInstance()

    val notesList = mutableStateListOf<NoteModel>()
    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow.asSharedFlow()
    val _scope = viewModelScope

    init {

        _scope.launch {
            val items = noteUseCases.getAll.execute()
            notesList.addAll(items)
        }

        _scope.launch {
            noteUseCases.listen.execute().collect { event ->
                when (event) {
                    is NoteEvents.Insertion -> notesList.add(event.value)
                    is NoteEvents.Updation -> updateItemInNotesList(event.value)
                    is NoteEvents.Deletion -> {
                        val itemId = event.id
                        val itemIndex = notesList.indexOfFirst { it.id == itemId }

                        if (itemIndex != -1) {
                            notesList.removeAt(itemIndex)
                        }
                    }
                }
            }
        }
    }

    fun listItemOnClick(id: Int) = _scope.launch(Dispatchers.Main) {
        Log.d(TAG, "listItemOnClick: $id")
        val route = HomeRoutes.ADD_NOTE + "/$id"
        _eventFlow.emit(HomeEvent.NavigateNext(route))
    }

    fun addNewNote() {
        Log.d(TAG, "addNewNote: ")
    }

    fun saveNote(value: NoteModel) = _scope.launch {

    }

    fun updateItemInNotesList(value: NoteModel) {
            val itemIndex = notesList.indexOfFirst { it.id == value.id }
            notesList[itemIndex] = value
    }


    sealed class HomeEvent {
        data class NavigateNext(val route: String): HomeEvent()

    }


}