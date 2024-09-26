package com.rashidsaleem.notesapp.feature_core.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert
    fun insertItem(item: NoteEntity): Long

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE id = :id LIMIT 1")
    fun getItem(id: Int): NoteEntity

    @Query("DELETE FROM notes where id = :id")
    fun deleteItem(id: Int)

    @Update
    fun updateItem(item: NoteEntity)

}