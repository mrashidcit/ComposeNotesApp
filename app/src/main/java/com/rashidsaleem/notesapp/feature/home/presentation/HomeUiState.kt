package com.rashidsaleem.notesapp.feature.home.presentation

import com.rashidsaleem.notesapp.feature.home.domain.models.Note
import com.rashidsaleem.notesapp.feature.home.domain.models.dummyNotes

data class HomeUiState(
    val notes: List<Note> = listOf()
)


fun previewHomeUiState() = HomeUiState(
    notes = dummyNotes()
)