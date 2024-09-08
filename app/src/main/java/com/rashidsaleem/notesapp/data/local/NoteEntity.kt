package com.rashidsaleem.notesapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rashidsaleem.notesapp.models.NoteModel

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
)

fun NoteModel.toEntity(): NoteEntity {
    return NoteEntity(
        id = if(id != -1) id else null,
        title = title,
        description = description
    )
}

fun NoteEntity.toModel(): NoteModel {
    return NoteModel(
        id = id ?: -1,
        title = title ?: "",
        description = description ?: "",
    )
}



