package com.rashidsaleem.notesapp.core.data.repository

import com.rashidsaleem.notesapp.core.data.local.AppDatabase
import com.rashidsaleem.notesapp.core.data.local.entity.toNote
import com.rashidsaleem.notesapp.core.data.local.entity.toNoteEntity
import com.rashidsaleem.notesapp.core.domain.model.Note
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val database: AppDatabase,
): NotesRepository {
    override suspend fun addNote(note: Note) {
        database
            .noteDao()
            .insertNote(note.toNoteEntity())
    }

    override suspend fun getNotes(): Flow<List<Note>> {
        return database
            .noteDao()
            .getAll()
            .map { it ->
                it.map {
                    it.toNote()
                }
            }
    }

    override suspend fun get(id: Int): Note {
        return database
            .noteDao()
            .get(id)
            .toNote()
    }

    override suspend fun delete(id: Int) {
        val noteEntity = database
            .noteDao()
            .get(id)
        database
            .noteDao()
            .delete(noteEntity)
    }
}