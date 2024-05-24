package com.rashidsaleem.notesapp.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.core.util.Routes
import com.rashidsaleem.notesapp.feature.home.domain.models.Note
import com.rashidsaleem.notesapp.feature.home.domain.useCase.GetNotesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel constructor(
    private val getNotesUseCase: GetNotesUseCase = GetNotesUseCase(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {

        val result = getNotesUseCase.execute()

        _uiState.update {
            it.copy(
                notes = result
            )
        }
    }

    fun action(action: HomeAction) {
        when (action) {
            HomeAction.AddNewNote -> addNewNote()
            is HomeAction.ItemOnClick -> itemOnClick(action.value)
        }
    }

    private fun itemOnClick(note: Note) = viewModelScope.launch {
        _event.emit(HomeEvent.NavigateNext(Routes.ADD_EDIT_NOTE, note.id))
    }

    private fun addNewNote() = viewModelScope.launch {
        _event.emit(HomeEvent.NavigateNext(Routes.ADD_EDIT_NOTE, -1))
    }

}