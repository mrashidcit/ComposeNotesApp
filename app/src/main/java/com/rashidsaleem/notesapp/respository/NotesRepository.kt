package com.rashidsaleem.notesapp.respository

import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes

class NotesRepository private constructor() {

    val items = arrayListOf<NoteModel>().apply {
        addAll(dummyNotes())
    }

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

    fun insert(item: NoteModel): Int {
        val newId = items.size + 1
        val newNote = item.copy(
            id = newId
        )
        items.add(newNote)
        return newId
    }

    fun update(item: NoteModel) {
        val itemIndex = items.indexOfFirst { it.id == item.id }
        items[itemIndex] = item

    }

}