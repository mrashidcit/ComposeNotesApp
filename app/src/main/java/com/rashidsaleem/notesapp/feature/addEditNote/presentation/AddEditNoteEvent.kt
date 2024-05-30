package com.rashidsaleem.notesapp.feature.addEditNote.presentation

sealed class AddEditNoteEvent {
    data object NavigateBack: AddEditNoteEvent()
    data class ShowToast(val message: String): AddEditNoteEvent()
}