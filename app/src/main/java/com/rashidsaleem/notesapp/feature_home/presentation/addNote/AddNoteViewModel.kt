package com.rashidsaleem.notesapp.feature_home.presentation.addNote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.feature_home.domain.models.NoteModel
import com.rashidsaleem.notesapp.feature_home.data.respository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.useCases.NoteUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddNoteViewModel(
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val TAG = "AddNoteViewModel"

    private val _scope = viewModelScope
    private val _noteUseCases: NoteUseCases = NoteUseCases.getInstance()
    private var _noteId: Int = -1
    private var _title: MutableStateFlow<String>  = MutableStateFlow("")
    val title = _title.asStateFlow()
    private var _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()
    private var _showConfirmationDialog = MutableStateFlow(false)
    val showConfirmationDialog = _showConfirmationDialog.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
        val noteId = savedStateHandle
            .get<Int>("id") ?: -1
        _noteId = noteId

        Log.d(TAG, "init: noteId = $noteId")

        _scope.launch {
            if (noteId != -1) {
                val note = _noteUseCases.g repository.get(noteId)
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

    fun backIconOnClick() = _scope.launch {

        val noteModel = NoteModel(
            id = _noteId,
            title = _title.value,
            description = _description.value,
        )

        // Save Note
        val isNoteEmpty = noteModel.let {
            it.title.isEmpty() && it.description.isEmpty()
        }
        if (isNoteEmpty) return@launch

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

    fun showConfirmationDialog(value: Boolean) {
        _showConfirmationDialog.value = value
    }

    fun deleteNote() {
        _scope.launch(Dispatchers.IO) {
            repository.delete(_noteId)
            showConfirmationDialog(false)
            _scope.launch(Dispatchers.Main) {
                _event.emit(Event.NavigateBack)
            }
        }

    }


    sealed class Event {
        data object NavigateBack: Event()
    }

}