package com.rashidsaleem.notesapp.core.data.respository

import com.rashidsaleem.notesapp.core.data.local.AppDatabase
import com.rashidsaleem.notesapp.core.data.local.NoteEntity
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import com.rashidsaleem.notesapp.core.domain.models.toEntity
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class NotesRepositoryImpl private constructor() : NotesRepository {

    val dao: NotesDao = AppDatabase.getInstance().notesDao()

    private val _newNoteInsertionListener = MutableSharedFlow<NoteModel>()
    override val newNoteInsertionListener: SharedFlow<NoteModel> = _newNoteInsertionListener.asSharedFlow()

    private val _updateNoteListener = MutableSharedFlow<NoteModel>()
    override val updateNoteListener: SharedFlow<NoteModel> = _updateNoteListener.asSharedFlow()

    private val _deleteNoteListener = MutableSharedFlow<Int>()
    override val deleteNoteListener: SharedFlow<Int> = _deleteNoteListener.asSharedFlow()





    companion object {

        private var _instance: NotesRepositoryImpl? = null

        fun getInstance(): NotesRepositoryImpl {
            if (_instance == null)
                _instance = NotesRepositoryImpl()

            return _instance as NotesRepositoryImpl

        }

    }

    override suspend fun getAll(): List<NoteEntity> {
        return dao.getAll()
    }

    override suspend fun get(id: Int) : NoteModel {
        return dao.getItem(id).toModel()
    }

    override suspend fun insert(item: NoteModel): Int {
        val newId = dao.insertItem(item.toEntity()).toInt()
        val newNote = item.copy(
            id = newId
        )
        _newNoteInsertionListener.emit(newNote)
        return newId
    }

    override suspend fun update(item: NoteModel) {
        dao.updateItem(item.toEntity())
        _updateNoteListener.emit(item)

    }

    override suspend fun delete(id: Int) {
        dao.deleteItem(id)

        _deleteNoteListener.emit(id)
    }

}