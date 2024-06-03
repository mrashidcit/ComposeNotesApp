package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature.addEditNote.domain.useCase.AddNoteUseCase
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

    private val TAG = "AddEditNoteViewModel"
    private val getNoteDetailUseCase: GetNoteDetailUseCase = GetNoteDetailUseCase(NotesApp.repository)
    private val addNoteUseCase: AddNoteUseCase = AddNoteUseCase(NotesApp.repository)
    private val deleteNoteUseCase: DeleteNoteUseCase = DeleteNoteUseCase(NotesApp.repository)

    private val _scope = viewModelScope
    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState = _uiState.asStateFlow()
    private val _event = MutableSharedFlow<AddEditNoteEvent>()
    val event = _event.asSharedFlow()
    private var _backOperationInProgress: Boolean = false

    init {
        val noteId: Int = savedStateHandle["note_id"] ?: 0
        Log.d(TAG, "init: noteId = $noteId")
        _scope.launch {
            if (noteId != 0) {
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

    private fun deleteIconOnClick() = _scope.launch {
        _uiState.update {
            it.copy(
                showConfirmationDialog = true
            )
        }
    }

    private fun backIconOnClick() = _scope.launch {

        if (_backOperationInProgress) return@launch
        _backOperationInProgress = true
        val note = uiState.value.note
        addNoteUseCase.execute(note)

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