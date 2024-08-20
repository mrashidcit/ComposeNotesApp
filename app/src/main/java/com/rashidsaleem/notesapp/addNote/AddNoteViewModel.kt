package com.rashidsaleem.notesapp.addNote

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.respository.NotesRepository
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
    private val repository: NotesRepository = NotesRepository.getInstance()
    private var _noteId: Int = -1
    private var _title: MutableStateFlow<String>  = MutableStateFlow("")
    val title = _title.asStateFlow()
    private var _description = MutableStateFlow<String>("")
    val description = _description.asStateFlow()

    private val _event = MutableSharedFlow<Event>()
    val event = _event.asSharedFlow()

    init {
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


    sealed class Event {
        data object NavigateBack: Event()
    }

}