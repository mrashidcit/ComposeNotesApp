package com.rashidsaleem.notesapp.feature_addNote.presentation

sealed interface AddNoteAction {
    data class TitleOnValueChange(val value: String): AddNoteAction
    data class DescriptionOnValueChange(val value: String): AddNoteAction
    data object BackIconOnClick: AddNoteAction
    data object HideConfirmationDialog: AddNoteAction
    data object ShowConfirmationDialog: AddNoteAction
    data object DeleteNote: AddNoteAction
}

sealed class AddNoteEvent {
    data object NavigateBack: AddNoteEvent()
}


