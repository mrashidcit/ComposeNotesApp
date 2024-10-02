package com.rashidsaleem.notesapp.respository

import com.rashidsaleem.notesapp.data.local.AppDatabase
import com.rashidsaleem.notesapp.data.local.NotesDao
import com.rashidsaleem.notesapp.data.local.toModel
import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes
import com.rashidsaleem.notesapp.models.toEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepository private constructor() {

    val dao: NotesDao = AppDatabase.getInstance().notesDao()

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

    suspend fun getAll(): List<NoteModel> {
        return dao.getAll().map {
            it.toModel()
        }
    }

    suspend fun get(id: Int) : NoteModel {
        return dao.getItem(id).toModel()
    }

    suspend fun insert(item: NoteModel): Int {
        val newId = dao.insertItem(item.toEntity()).toInt()
        val newNote = item.copy(
            id = newId
        )
        _newNoteInsertionListener.emit(newNote)
        return newId
    }

    suspend fun update(item: NoteModel) {
        dao.updateItem(item.toEntity())
        _updateNoteListener.emit(item)

    }

    suspend fun delete(id: Int) {
        dao.deleteItem(id)

        _deleteNoteListener.emit(id)
    }

}