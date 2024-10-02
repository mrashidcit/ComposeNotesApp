package com.rashidsaleem.notesapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rashidsaleem.notesapp.models.NoteModel

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val title: String?,
    val description: String?,
)

fun NoteEntity.toModel(): NoteModel {
    return NoteModel(
        id = this.id ?: -1,
        title = this.title ?: "",
        description = this.description ?: ""
    )


}