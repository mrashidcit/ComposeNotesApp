package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import com.rashidsaleem.notesapp.feature.home.domain.models.Note

data class AddEditNoteUiState(
    val note: Note = Note(-1, "","")
)
