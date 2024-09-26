package com.rashidsaleem.notesapp.feature_addNote.presentation

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel
import com.rashidsaleem.notesapp.feature_core.data.respository.NotesRepository
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

    private val repository: NotesRepository = NotesRepository.getInstance()
    private var _noteId: Int = -1
    private var _title: MutableStateFlow<String>  = MutableStateFlow("")
    val title = _title.asStateFlow()
    private var _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()
    private val _showConfirmationDialog = MutableStateFlow<Boolean>(false)
    val showConfirmationDialog = _showConfirmationDialog.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()
    val _scope = viewModelScope

    init {

        _scope.launch(Dispatchers.IO) {
            val noteId = savedStateHandle
                .get<Int>("id") ?: -1
            _noteId = noteId

            Log.d(TAG, "init: noteId = $noteId")

            if (noteId != -1) {
                val note = repository.get(noteId)
                _title.value = note.title
                _description.value = note.description
            }
        }

    }

    fun titleOnValueChange(value: String) {
        Log.d(TAG, "titleOnValueChange: title = ${title.value}")
        _title.value = value
    }

    fun descriptionOnValueChange(value: String) {
        _description.value = value
    }

    fun backIconOnClick() = viewModelScope.launch(Dispatchers.IO) {

        val noteModel = NoteModel(
            id = _noteId,
            title = _title.value,
            description = _description.value,
        )

        // Save Note
        if (noteModel.id == -1) {
            repository.insert(noteModel)
        } else {
            repository.update(noteModel)
        }

        // Navigate Back
        viewModelScope.launch(Dispatchers.Main) {
            _event.emit(Event.NavigateBack)
        }

    }

    fun hideConfirmationDialog() {
        _showConfirmationDialog.value = false
    }

    fun showConfirmationDialog() {
        _showConfirmationDialog.value = true
    }

    fun deleteNote() = viewModelScope.launch(Dispatchers.IO) {
        val itemId = _noteId
        repository.delete(itemId)

        hideConfirmationDialog()
        // Navigate Back
        viewModelScope.launch(Dispatchers.Main) {
            _event.emit(Event.NavigateBack)
        }
    }


    sealed class Event {
        data object NavigateBack: Event()
    }

}