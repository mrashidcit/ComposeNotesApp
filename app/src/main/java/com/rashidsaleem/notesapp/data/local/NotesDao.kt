package com.rashidsaleem.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getAll(): List<NoteEntity>

    @Insert
    fun insertItem(item: NoteEntity): Long

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    fun getItem(id: Int): NoteEntity

    @Update
    fun updateItem(item: NoteEntity)

    @Query("DELETE FROM notes WHERE id = :id")
    fun deleteItem(id: Int)


}