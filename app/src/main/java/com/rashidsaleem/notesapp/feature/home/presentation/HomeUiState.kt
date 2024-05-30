package com.rashidsaleem.notesapp.feature.home.presentation

import com.rashidsaleem.notesapp.core.domain.model.Note
import com.rashidsaleem.notesapp.core.domain.model.dummyNotes

data class HomeUiState(
    val notes: List<Note> = listOf()
)


fun previewHomeUiState() = HomeUiState(
    notes = dummyNotes()
)