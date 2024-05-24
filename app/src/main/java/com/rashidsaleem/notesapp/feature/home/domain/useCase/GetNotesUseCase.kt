package com.rashidsaleem.notesapp.feature.home.domain.useCase

import com.rashidsaleem.notesapp.feature.home.domain.models.Note
import com.rashidsaleem.notesapp.feature.home.domain.models.dummyNotes

class GetNotesUseCase {

    fun execute(): List<Note> {
        return dummyNotes()
    }

}