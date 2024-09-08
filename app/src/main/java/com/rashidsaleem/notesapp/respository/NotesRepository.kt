package com.rashidsaleem.notesapp.respository

import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.data.local.NoteDao
import com.rashidsaleem.notesapp.data.local.toEntity
import com.rashidsaleem.notesapp.data.local.toModel
import com.rashidsaleem.notesapp.models.NoteModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepository private constructor(
    private val noteDao: NoteDao
) {

    companion object {

        private var _instance: NotesRepository? = null
        fun getInstance(): NotesRepository {
            if (_instance == null)
                _instance = NotesRepository(NotesApp.appDatabase.noteDao())

            return _instance as NotesRepository
        }
    }

    private val _insertionListener: MutableSharedFlow<NoteModel> = MutableSharedFlow<NoteModel>()
    val insertionListener: SharedFlow<NoteModel> = _insertionListener.asSharedFlow()

    private val _updateListener: MutableSharedFlow<NoteModel> = MutableSharedFlow<NoteModel>()
    val updateListener: SharedFlow<NoteModel> = _updateListener.asSharedFlow()

    private val _deleteListener: MutableSharedFlow<Int> = MutableSharedFlow<Int>()
    val deleteListener: SharedFlow<Int> = _deleteListener.asSharedFlow()


    fun getAll(): List<NoteModel> {
        val items = noteDao.getAll()
        return items.map { it.toModel() }
    }

    fun get(id: Int) : NoteModel {
        val item = noteDao.get(id)
        return item.toModel()
    }

    suspend fun insert(item: NoteModel): Int {

        val newEntity = item.toEntity()
        val newId = noteDao.insertItem(newEntity)

        val newNote = item.copy(
            id = newId
        )
        _insertionListener.emit(newNote)
        return newId
    }

    suspend fun update(item: NoteModel) {
        val updatedEntity = item.toEntity()
        noteDao.updateItem(updatedEntity)
        _updateListener.emit(item)
    }

    suspend fun delete(noteId: Int) {
        noteDao.delete(noteId)
        _deleteListener.emit(noteId)
    }

}