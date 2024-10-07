package com.rashidsaleem.notesapp.feature_addNote.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.feature_addNote.domain.AddNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.DeleteNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.GetNoteUseCase
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val TAG = "AddNoteViewModel"

    private val _getNoteUseCase: GetNoteUseCase = GetNoteUseCase.getInstance()
    private val _addNoteUseCase: AddNoteUseCase = AddNoteUseCase.getInstance()
    private val _deleteNoteUseCase: DeleteNoteUseCase = DeleteNoteUseCase.getInstance()
    private var _noteId: Int = -1
    private var _title: MutableStateFlow<String>  = MutableStateFlow("")
    val title = _title.asStateFlow()
    private var _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()
    private val _showConfirmationDialog = MutableStateFlow<Boolean>(false)
    val showConfirmationDialog = _showConfirmationDialog.asStateFlow()
    private val _scope = viewModelScope

    private val _event = MutableSharedFlow<AddNoteEvent>()
    val event = _event.asSharedFlow()

    init {
        val noteId = savedStateHandle
            .get<Int>("id") ?: -1
        _noteId = noteId

        Log.d(TAG, "init: noteId = $noteId")

        _scope.launch(Dispatchers.IO) {
            if (noteId != -1) {
                val note = _getNoteUseCase.execute(noteId)
                _title.value = note.title
                _description.value = note.description
            }
        }

    }

    fun action(action: AddNoteAction) {
        when (action) {
            AddNoteAction.BackIconOnClick -> backIconOnClick()
            AddNoteAction.DeleteNote -> deleteNote()
            is AddNoteAction.DescriptionOnValueChange -> descriptionOnValueChange(action.value)
            AddNoteAction.HideConfirmationDialog -> hideConfirmationDialog()
            AddNoteAction.ShowConfirmationDialog -> showConfirmationDialog()
            is AddNoteAction.TitleOnValueChange -> titleOnValueChange(action.value)
        }
    }

    private fun titleOnValueChange(value: String) {
        Log.d(TAG, "titleOnValueChange: title = ${title.value}")
        _title.value = value
    }

    private fun descriptionOnValueChange(value: String) {
        _description.value = value
    }

    private fun backIconOnClick() = viewModelScope.launch(Dispatchers.IO) {

        val noteModel = NoteModel(
            id = _noteId,
            title = _title.value,
            description = _description.value,
        )

        // Save Note
        _addNoteUseCase.execute(noteModel)

        // Navigate Back
        viewModelScope.launch(Dispatchers.Main) {
            _event.emit(AddNoteEvent.NavigateBack)
        }

    }

    private fun hideConfirmationDialog() {
        _showConfirmationDialog.value = false
    }

    private fun showConfirmationDialog() {
        _showConfirmationDialog.value = true
    }

    private fun deleteNote() = viewModelScope.launch(Dispatchers.IO) {
        val itemId = _noteId
        _deleteNoteUseCase.execute(itemId)

        hideConfirmationDialog()
        // Navigate Back
        viewModelScope.launch(Dispatchers.Main) {
            _event.emit(AddNoteEvent.NavigateBack)
        }
    }



}