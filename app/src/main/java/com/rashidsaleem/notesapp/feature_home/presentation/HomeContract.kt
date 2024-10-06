package com.rashidsaleem.notesapp.feature_home.presentation

sealed interface HomeAction {
    data class ListItemOnClick(val value: Int): HomeAction
    data object AddNewNote: HomeAction
}

sealed interface HomeEvent {
    data class NavigateNext(val route: String): HomeEvent

}