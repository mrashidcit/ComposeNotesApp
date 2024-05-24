package com.rashidsaleem.notesapp.feature.home.domain.models

data class Note(
    val id: Int,
    val title: String,
    val description: String,
)



fun dummyNotes(): List<Note> {
    val result = arrayListOf<Note>().apply {
        for (i in 1..20) {
            add(
                Note(1, "Title$i", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")
            )
        }
    }
    return result
}
