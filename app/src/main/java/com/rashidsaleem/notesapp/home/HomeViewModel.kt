package com.rashidsaleem.notesapp.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.rashidsaleem.notesapp.models.NoteModel
import com.rashidsaleem.notesapp.models.dummyNotes

class HomeViewModel: ViewModel() {

    private val TAG = "HomeViewModel"

    val notes = mutableStateListOf<NoteModel>().apply {
        addAll(dummyNotes())
    }

    fun listItemOnClick(id: Int) {
        Log.d(TAG, "listItemOnClick: $id")
    }

    fun addNewNote() {
        Log.d(TAG, "addNewNote: ")
    }

    fun saveNote(value: NoteModel) {
        Log.d(TAG, "saveNote: $value")
        notes.add(value)
    }



}