package com.rashidsaleem.notesapp.models

import com.rashidsaleem.notesapp.data.local.NoteEntity

data class NoteModel(
    val id: Int,
    val title: String,
    val description: String,
)

fun NoteModel.toEntity(): NoteEntity {
    return NoteEntity(
        id = if (id != -1) id else null,
        title = title,
        description = description,
    )
}



fun dummyNotes(): List<NoteModel> {
    val items = arrayListOf<NoteModel>()

//    for (i in 1..20) {
    for (i in 1..3) {
        items.add(
            NoteModel(i, "Title $i", "Description $i")
        )
    }

    return items
}