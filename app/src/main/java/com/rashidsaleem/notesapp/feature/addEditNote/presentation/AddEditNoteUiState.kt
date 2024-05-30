package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import com.rashidsaleem.notesapp.core.domain.model.Note

data class AddEditNoteUiState(
    val note: Note = Note(-1, "",""),
    val showConfirmationDialog: Boolean = false,
)
