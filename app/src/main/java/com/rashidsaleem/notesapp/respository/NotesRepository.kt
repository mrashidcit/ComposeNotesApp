package com.rashidsaleem.notesapp.respository

import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepository private constructor() {

    private val items = arrayListOf<NoteModel>().apply {
        addAll(dummyNotes())
    }

    private val _insertionListener: MutableSharedFlow<NoteModel> = MutableSharedFlow<NoteModel>()
    val insertionListener: SharedFlow<NoteModel> = _insertionListener.asSharedFlow()

    private val _updateListener: MutableSharedFlow<NoteModel> = MutableSharedFlow<NoteModel>()
    val updateListener: SharedFlow<NoteModel> = _updateListener.asSharedFlow()



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
        _insertionListener.emit(newNote)
        return newId
    }

    suspend fun update(item: NoteModel) {
        val itemIndex = items.indexOfFirst { it.id == item.id }
        items[itemIndex] = item
        _updateListener.emit(item)
    }

}