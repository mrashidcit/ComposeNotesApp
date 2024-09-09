package com.rashidsaleem.notesapp.respository

import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepository private constructor() {

    val items = arrayListOf<NoteModel>().apply {
        addAll(dummyNotes())
    }
    private val _newNoteInsertionListener = MutableSharedFlow<NoteModel>()
    val newNoteInsertionListener: SharedFlow<NoteModel> = _newNoteInsertionListener.asSharedFlow()

    private val _updateNoteListener = MutableSharedFlow<NoteModel>()
    val updateNoteListener: SharedFlow<NoteModel> = _updateNoteListener.asSharedFlow()

    private val _deleteNoteListener = MutableSharedFlow<Int>()
    val deleteNoteListener: SharedFlow<Int> = _deleteNoteListener.asSharedFlow()





    companion object {

        private var _instance: NotesRepository? = null

        fun getInstance(): NotesRepository {
            if (_instance == null)
                _instance = NotesRepository()

            return _instance as NotesRepository

        }

    }

    fun getAll(): List<NoteModel> {
        return items
    }

    fun get(id: Int) : NoteModel {
        return items.first { it.id == id }
    }

    suspend fun insert(item: NoteModel): Int {
        val newId = items.size + 1
        val newNote = item.copy(
            id = newId
        )
        items.add(newNote)

        _newNoteInsertionListener.emit(newNote)
        return newId
    }

    suspend fun update(item: NoteModel) {
        val itemIndex = items.indexOfFirst { it.id == item.id }
        items[itemIndex] = item

        _updateNoteListener.emit(item)

    }

    suspend fun delete(id: Int) {
        val itemIndex = items.indexOfFirst { it.id == id }

        if (itemIndex != -1) {
            items.removeAt(itemIndex)
        }

        _deleteNoteListener.emit(id)
    }

}