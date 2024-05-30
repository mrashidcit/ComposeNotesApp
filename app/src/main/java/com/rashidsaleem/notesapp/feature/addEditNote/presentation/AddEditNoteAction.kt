package com.rashidsaleem.notesapp.feature.addEditNote.presentation

sealed class AddEditNoteAction {
    data object BackIconOnClick: AddEditNoteAction()
    data object DeleteIconOnClick: AddEditNoteAction()
    data class UpdateTitle(val value: String): AddEditNoteAction()
    data class UpdateDescription(val value: String): AddEditNoteAction()
    data class ShowConfirmationDialog(val value: Boolean): AddEditNoteAction()
    data object DeletionConfirmed: AddEditNoteAction()
}