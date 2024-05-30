package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase.DeleteNoteUseCase
import com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase.GetNoteDetailUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val getNoteDetailUseCase: GetNoteDetailUseCase = GetNoteDetailUseCase()
    private val deleteNoteUseCase: DeleteNoteUseCase = DeleteNoteUseCase()

    private val _scope = viewModelScope
    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState = _uiState.asStateFlow()

    private val _event = MutableSharedFlow<AddEditNoteEvent>()
    val event = _event.asSharedFlow()

    init {
        val noteId: Int = savedStateHandle["note_id"] ?: -1
        _scope.launch {
            if (noteId != -1) {
                getNoteDetailUseCase
                    .execute(noteId)
                    ?.let { noteDetail ->
                        _uiState.update {
                            it.copy(
                                note = noteDetail
                            )
                        }
                    }
            }
        }

    }

    private fun updateUiState(
        title: String? = null,
        description: String? = null,
    ) {
        _uiState.update {
            it.copy(
                note = it.note.copy(
                    title = title ?: it.note.title,
                    description = description ?: it.note.description
                )
            )
        }
    }

    fun action(action: AddEditNoteAction) {
        when (action) {
            AddEditNoteAction.BackIconOnClick -> backIconOnClick()
            AddEditNoteAction.DeleteIconOnClick -> deleteIconOnClick()
            is AddEditNoteAction.UpdateDescription -> updateDescription(action.value)
            is AddEditNoteAction.UpdateTitle -> updateTitle(action.value)
            AddEditNoteAction.DeletionConfirmed -> deletionConfirmed()
            is AddEditNoteAction.ShowConfirmationDialog -> showConfirmationDialog(action.value)
        }
    }

    private fun showConfirmationDialog(value: Boolean) {
        _uiState.update {
            it.copy(
                showConfirmationDialog = value
            )
        }
    }

    private fun deletionConfirmed() = _scope.launch {
        val noteId = _uiState.value.note.id
        deleteNoteUseCase.execute(noteId)
        _event.emit(AddEditNoteEvent.ShowToast("Successfully Deleted!"))
        _event.emit(AddEditNoteEvent.NavigateBack)
        _uiState.update {
            it.copy(
                showConfirmationDialog = false
            )
        }
    }

    private fun deleteIconOnClick() {
        _uiState.update {
            it.copy(
                showConfirmationDialog = true
            )
        }
    }

    private fun backIconOnClick() = _scope.launch {
        _event.emit(AddEditNoteEvent.NavigateBack)
    }

    private fun updateDescription(value: String) {
        updateUiState(
            description = value
        )
    }

    private fun updateTitle(value: String) {
        updateUiState(
            title = value,
        )
    }

}