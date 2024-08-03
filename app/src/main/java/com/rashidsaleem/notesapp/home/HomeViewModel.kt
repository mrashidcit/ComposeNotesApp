package com.rashidsaleem.notesapp.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes

class HomeViewModel: ViewModel() {

    private val TAG = "HomeViewModel"

    private val _notes = ArrayList<NoteModel>(dummyNotes())
    val notes = _notes.toList()

    fun listItemOnClick(id: Int) {
        Log.d(TAG, "listItemOnClick: $id")
    }

    fun addNewNote() {
        Log.d(TAG, "addNewNote: ")
    }

}