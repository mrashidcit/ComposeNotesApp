package com.rashidsaleem.notesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item: NoteEntity): Long

    @Query("SELECT * FROM notes")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes where id = :id LIMIT 1")
    fun get(id: Int): NoteEntity

    @Update
    fun updateItem(item: NoteEntity)

    @Query("DELETE FROM notes where id = :id")
    fun delete(id: Int)

    @Delete
    fun delete(note: NoteEntity)



}