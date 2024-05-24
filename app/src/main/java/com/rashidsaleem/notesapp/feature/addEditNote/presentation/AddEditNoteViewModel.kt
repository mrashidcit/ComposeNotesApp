package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddEditNoteViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState = _uiState.asStateFlow()

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
        }
    }

    private fun deleteIconOnClick() {
        TODO("Not yet implemented")
    }

    private fun backIconOnClick() {
        TODO("Not yet implemented")
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