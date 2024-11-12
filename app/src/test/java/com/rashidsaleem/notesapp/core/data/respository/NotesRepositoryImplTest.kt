package com.rashidsaleem.notesapp.core.data.respository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NoteEntity
import com.rashidsaleem.notesapp.core.data.local.toModel
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val note1 = NoteEntity(1, "Note 1 Title", "My first note")
private val note2 = NoteEntity(2, "Note 2 Title", "2nd note")
private val note3 = NoteEntity(3, "Note 3 Title", "3rd note")
private val note4 = NoteEntity(null, "Note 4 Title", "4th note")
private val note5 = NoteEntity(5, "Note 5 Title", "5th note")


class NotesRepositoryImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNotesDao: FakeNotesDao
    private lateinit var repository: NotesRepositoryImpl

    @Before
    fun setupRepository() {
        val allItems = arrayListOf(note1, note2, note3)
        fakeNotesDao = FakeNotesDao(allItems)
        repository = NotesRepositoryImpl(fakeNotesDao)
    }

    @Test
    fun getAll_return3Items() = runTest {

        // When - get all the notes
        val result = repository.getAll()

        // Then - expected 3 items
        Truth.assertThat(result.size).isEqualTo(3)

    }

    @Test
    fun getAll_deleteANote_return2Items() = runTest {

        // When - delete a note & get the all notes
        val noteId = note3.id ?: 0
        repository.delete(noteId)
        val result = repository.getAll()

        // Then - expected 2 items
        Truth.assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun getAll_insertNewNote_return4Items() = runTest {

        // When - insert a new note & get all items
        val newNoteModel = note4.toModel()
        repository.insert(newNoteModel)
        val result = repository.getAll()

        // Then - expected 4 items
        Truth.assertThat(result.size).isEqualTo(4)

    }

    @Test
    fun getNote_returnOneItem() = runTest {

        // When - get first note
        val note1Model = note1.toModel()
        val result = repository.get(note1Model.id)

        // Then - correct note in response
        Truth.assertThat(result).isEqualTo(note1Model)
    }

    @Test
    fun getNote_passInvalidNoteId_returnOnItem() = runTest {

        // When - pass invalid note id
        val noteId = 25
        val result = repository.get(noteId)

        // Then - null in resposne
        Truth.assertThat(result).isEqualTo(null)
    }

    @Test
    fun insert_newNote_returnOneItem() = runTest {

        // When - new note
        val newNoteModel = note4.toModel()
        val newNoteId = repository.insert(newNoteModel)
        val updatedNoteModel = newNoteModel.copy(
            id = newNoteId
        )
        val result = repository.get(newNoteId)


        // Then - new note in response
        Truth.assertThat(result).isEqualTo(updatedNoteModel)

    }

    @Test
    fun insertNote_existingNoteAsInsertNote_returnOneItem() = runTest {

        // Given - use existing note to insert
        val note1Model = note1.toModel()

        // When - insert the note
        repository.insert(note1Model)
        val result = repository.get(note1Model.id)

        // Then - expecting one item
        Truth.assertThat(result).isEqualTo(note1Model)

    }


    @Test
    fun insertNote_passANewNoteWithIdWithDoesnotExistInDB_returnOneItem() = runTest {

        // Given - newNote with Id
        val newNoteModel = note5.toModel()

        // When - insert the note
        val newNoteId = repository.insert(newNoteModel)
        val updatedNoteModel = newNoteModel.copy(
            id = newNoteId
        )
        val result = repository.get(newNoteId)

        // Then - expecting note item
        Truth.assertThat(result).isEqualTo(updatedNoteModel)
    }

    @Test
    fun updateNote_returnUpdatedNoteItem() = runTest {

        // Given - updated Note
        val updatedNoteModel = note1.copy(
            title = "This is Updated Title"
        ).toModel()

        // When - update the note
        repository.update(updatedNoteModel)
        val result = repository.get(updatedNoteModel.id)

        // Then - expected updated note
        Truth.assertThat(result).isEqualTo(updatedNoteModel)

    }

    @Test
    fun deleteNote_returnNull() = runTest {

        // Given - note id
        val noteId = note1.id ?: -1

        // When - delete the note
        repository.delete(noteId)
        val result = repository.get(noteId)

        // Then - expected null
        Truth.assertThat(result).isEqualTo(null)

    }

}












