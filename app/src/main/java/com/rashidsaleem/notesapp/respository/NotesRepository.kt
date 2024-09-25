package com.rashidsaleem.notesapp.respository

import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.core.data.AppDatabase
import com.rashidsaleem.notesapp.core.data.NoteDao
import com.rashidsaleem.notesapp.core.data.toNoteModel
import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes
import com.rashidsaleem.notesapp.models.toNoteEntity
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepository private constructor() {

    private val dao: NoteDao = AppDatabase.getInstance(NotesApp.appContext).noteDao()

    private val _newNoteInsertionListener = MutableSharedFlow<NoteModel>()
    val newNoteInsertionListener: SharedFlow<NoteModel> = _newNoteInsertionListener.asSharedFlow()

    private val _updateNoteInsertionListener = MutableSharedFlow<NoteModel>()
    val updateNoteInsertionListener: SharedFlow<NoteModel> = _updateNoteInsertionListener.asSharedFlow()



    companion object {

        private var _instance: NotesRepository? = null

        fun getInstance(): NotesRepository {
            if (_instance == null)
                _instance = NotesRepository()

            return _instance as NotesRepository

        }

    }

    fun getAll(): List<NoteModel> {
        return dao.getAll().map {
            it.toNoteModel()
        }
    }

    fun get(id: Int) : NoteModel {
        return dao.getItem(id).toNoteModel()
    }

    suspend fun insert(item: NoteModel): Int {

        val newEntity = item.toNoteEntity()

        val newItemId = dao.insertItem(newEntity).toInt()

        val newItem = item.copy(
            id = newItemId
        )

        _newNoteInsertionListener.emit(newItem)

        return newItemId
    }

    suspend fun update(item: NoteModel) {

        val updatedEntity = item.toNoteEntity()
        dao.updateItem(updatedEntity)
        _updateNoteInsertionListener.emit(item)

    }

}