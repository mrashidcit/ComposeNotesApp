package com.rashidsaleem.notesapp.core.domain.repository

import com.rashidsaleem.notesapp.core.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    suspend fun addNote(note: Note)
    suspend fun getNotes(): Flow<List<Note>>
    suspend fun get(id: Int): Note
    suspend fun delete(id: Int)
}