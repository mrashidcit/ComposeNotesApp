package com.rashidsaleem.notesapp.feature.home.presentation

import com.rashidsaleem.notesapp.feature.home.domain.models.Note

sealed class HomeAction {
    data class ItemOnClick(val value: Note): HomeAction()
    data object AddNewNote: HomeAction()
}