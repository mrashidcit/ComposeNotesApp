package com.rashidsaleem.notesapp.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.core.util.Routes
import com.rashidsaleem.notesapp.core.domain.model.Note
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.AddEditNoteViewModel
import com.rashidsaleem.notesapp.feature.home.domain.useCase.GetNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    repository: NotesRepository,
    getNotesUseCase: GetNotesUseCase = GetNotesUseCase(repository),
) : ViewModel() {

    private val _scope = viewModelScope
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<HomeEvent>()
    val event = _event.asSharedFlow()

    init {

        _scope.launch(Dispatchers.IO) {
            getNotesUseCase.execute().collectLatest { items ->
                _uiState.update {
                    it.copy(
                        notes = items
                    )
                }
            }
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
        _event.emit(HomeEvent.NavigateNext(Routes.ADD_EDIT_NOTE, 0))
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repository: NotesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }
//        else if (modelClass.isAssignableFrom(AddEditNoteViewModel::class.java)) {
//            return AddEditNoteViewModel(repository) as T
//        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}