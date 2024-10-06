package com.rashidsaleem.notesapp.feature_home.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.Routes
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel
import com.rashidsaleem.notesapp.feature_home.domain.GetNotesUseCase
import com.rashidsaleem.notesapp.feature_home.domain.NoteEvents
import com.rashidsaleem.notesapp.feature_home.domain.NotesListenerUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val TAG = "HomeViewModel"

    private val getNotesUseCase: GetNotesUseCase = GetNotesUseCase()
    private val notesListenerUseCase: NotesListenerUseCase = NotesListenerUseCase()

    val notesList = mutableStateListOf<NoteModel>()
    private val _eventFlow = MutableSharedFlow<HomeEvent>()
    val eventFlow: SharedFlow<HomeEvent> = _eventFlow.asSharedFlow()
    val _scope = viewModelScope


    init {

        _scope.launch(Dispatchers.IO) {
            val items = getNotesUseCase.execute()
            Log.d(TAG, "items: ${items.size}")
            delay(500L)
            notesList.addAll(items)
        }

        _scope.launch {
            notesListenerUseCase.execute().collectLatest { event ->
                when (event) {
                    is NoteEvents.Insertion -> notesList.add(0, event.value)
                    is NoteEvents.Update -> {
                        val itemIndex = notesList.indexOfFirst { it.id == event.value.id }

                        if (itemIndex != -1) {
                            notesList[itemIndex] = event.value
                        }
                    }
                    is NoteEvents.Delete -> {
                        val itemIndex = notesList.indexOfFirst { it.id == event.value }

                        if (itemIndex != -1) {
                            notesList.removeAt(itemIndex)
                        }

                    }
                }

            }

        }
    }

    fun action(action: HomeAction) {
        when(action) {
            is HomeAction.ListItemOnClick -> listItemOnClick(action.value)
            HomeAction.AddNewNote -> addNewNote()
        }
    }

    private fun addNewNote() = _scope.launch {
        val route = Routes.ADD_NOTE + "/-1"
        _eventFlow.emit(HomeEvent.NavigateNext(route))
    }

    private fun listItemOnClick(id: Int) = _scope.launch(Dispatchers.Main) {
        Log.d(TAG, "list    ItemOnClick: $id")
        val route = Routes.ADD_NOTE + "/$id"
        _eventFlow.emit(HomeEvent.NavigateNext(route))
    }





}