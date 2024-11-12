package com.rashidsaleem.notesapp.feature_home.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NoteEntity
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val note1 = NoteEntity(1, "Note 1 Title", "My first note")
private val note2 = NoteEntity(2, "Note 2 Title", "2nd note")
private val note3 = NoteEntity(3, "Note 3 Title", "3rd note")
private val note4 = NoteEntity(null, "Note 4 Title", "4th note")
private val note5 = NoteEntity(5, "Note 5 Title", "5th note")

@OptIn(ExperimentalCoroutinesApi::class)
class GetNotesUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var items: ArrayList<NoteEntity>
    private lateinit var itemModels: List<NoteModel>
    private lateinit var fakeNotesDao: FakeNotesDao
    private lateinit var repository: NotesRepositoryImpl
    private lateinit var useCase: GetNotesUseCase

    @Before
    fun setupUseCase() {
        items = arrayListOf(
            note1,
            note2,
            note3
        )
        itemModels = items.map { it.toModel() }
        fakeNotesDao = FakeNotesDao(items)
        repository = NotesRepositoryImpl(fakeNotesDao)
        useCase = GetNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getAllNotes_return3Items() = runTest {

        // Given - setup the useCase

        // When - get all the notes
        val result = repository.getAll()

        // Then - expecting 3 items
        Truth.assertThat(result.size).isEqualTo(itemModels.size)
        Truth.assertThat(result).isEqualTo(itemModels)

    }

    @Test
    fun getAllNotes_insertNewNote_return4Items() = runTest {

        // Given - setup the useCase

        // When - insert a new note
        var newNote = note4.toModel()
        val newNoteId = repository.insert(newNote)
        newNote  = newNote.copy(
            id = newNoteId
        )
        val result = repository.getAll()

        // Then - expecting 4 items
        Truth.assertThat(result.size).isEqualTo(4)


    }


    @Test
    fun getAllNotes_deleteOneNote_return2Items() = runTest {

        // Given - setup the useCase

        // When - delete one note
        val noteId = note1.id ?: -1
        repository.delete(noteId)
        val result = repository.getAll()

        // Then - expecting 2 items
        Truth.assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun getAllNotes_updateOneNote_return3Items() = runTest {

        // Given - setup the useCase

        // When - update one note
        val updateNote = note1.toModel().copy(
            title = "Note title updated"
        )
        repository.update(updateNote)
        val result = repository.getAll()

        // Then - expecting 3 items
        Truth.assertThat(result.size).isEqualTo(3)

    }



}

















