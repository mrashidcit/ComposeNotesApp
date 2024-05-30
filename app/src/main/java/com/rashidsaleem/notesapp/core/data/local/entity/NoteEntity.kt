package com.rashidsaleem.notesapp.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rashidsaleem.notesapp.core.domain.model.Note

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val description: String?,
)


fun NoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title ?: "",
        description = this.description ?: "",
    )
}

