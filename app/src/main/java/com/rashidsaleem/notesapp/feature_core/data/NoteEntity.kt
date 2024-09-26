package com.rashidsaleem.notesapp.feature_core.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rashidsaleem.notesapp.feature_core.domain.models.NoteModel

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String?,
    val description: String?,
)


fun NoteEntity.toNoteModel(): NoteModel {
    return NoteModel(
        id = this.id ?: -1,
        title = this.title ?: "",
        description = this.description ?: "",
    )
}
