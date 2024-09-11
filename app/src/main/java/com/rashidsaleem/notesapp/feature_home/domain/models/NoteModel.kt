package com.rashidsaleem.notesapp.feature_home.domain.models

data class NoteModel(
    val id: Int,
    val title: String,
    val description: String,
)



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