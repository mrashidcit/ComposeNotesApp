package com.rashidsaleem.notesapp.feature.home.presentation

sealed class HomeEvent {
    data class NavigateNext(val route: String, val noteId: Int): HomeEvent()
}