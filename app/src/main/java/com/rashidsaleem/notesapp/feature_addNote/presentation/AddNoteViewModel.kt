package com.rashidsaleem.notesapp.feature_addNote.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.core.di.IODispatcher
import com.rashidsaleem.notesapp.core.di.MainDispatcher
import com.rashidsaleem.notesapp.feature_addNote.domain.AddNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.DeleteNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.GetNoteUseCase
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val _getNoteUseCase: GetNoteUseCase,
    private val _addNoteUseCase: AddNoteUseCase,
    private val _deleteNoteUseCase: DeleteNoteUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
): ViewModel() {

    private val TAG = "AddNoteViewModel"



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

        getNote(noteId)

    }

    private fun getNote(noteId: Int) {
        _scope.launch(ioDispatcher) {
            if (noteId != -1) {
                val note = _getNoteUseCase.execute(noteId) ?: return@launch
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

    private fun backIconOnClick() = viewModelScope.launch(ioDispatcher) {
        Log.d(TAG, "backIconOnClick: START")
        val noteModel = NoteModel(
            id = _noteId,
            title = _title.value,
            description = _description.value,
        )

        // Save Note
        _addNoteUseCase.execute(noteModel)

        // Navigate Back
        viewModelScope.launch(mainDispatcher) {
            _event.emit(AddNoteEvent.NavigateBack)
        }
        Log.d(TAG, "backIconOnClick: END")
    }

    private fun hideConfirmationDialog() {
        _showConfirmationDialog.value = false
    }

    private fun showConfirmationDialog() {
        _showConfirmationDialog.value = true
    }

    private fun deleteNote() = viewModelScope.launch(ioDispatcher) {
        val itemId = _noteId
        _deleteNoteUseCase.execute(itemId)

        hideConfirmationDialog()
        // Navigate Back
        viewModelScope.launch(mainDispatcher) {
            _event.emit(AddNoteEvent.NavigateBack)
        }
    }



}