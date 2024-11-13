package com.rashidsaleem.notesapp.feature_addNote.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NoteEntity
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.NotesEvent
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private val note1 = NoteEntity(1, "Note 1 Title", "My first note")
private val note2 = NoteEntity(2, "Note 2 Title", "2nd note")
private val note3 = NoteEntity(3, "Note 3 Title", "3rd note")
private val note4 = NoteEntity(null, "Note 4 Title", "4th note")
private val note5 = NoteEntity(5, "Note 5 Title", "5th note")

@OptIn(ExperimentalCoroutinesApi::class)
class AddNoteUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNotesDao: NotesDao
    private lateinit var repository: NotesRepository
    private lateinit var useCase: AddNoteUseCase
    private lateinit var listenNotesUseCase: ListenNotesUseCase

    private var testDispatcher = StandardTestDispatcher()

    @Before
    fun setUpUseCase() {
        val items = arrayListOf(
            note1, note2, note3,
        )
        fakeNotesDao = FakeNotesDao(items)
        repository = NotesRepositoryImpl(
            dao = fakeNotesDao
        )
        useCase = AddNoteUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        listenNotesUseCase = ListenNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )

    }

    @Test
    fun addNote_insertNewNote_returnNewInsertedNote() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase & listen the note events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNotesUseCase.execute().collect { newEvent ->
                events.add(newEvent)
            }
        }


        // When - insert the note
        var newNote = note4.toModel()
        useCase.execute(newNote)
        val result = events.firstOrNull()
            ?.let { it as NotesEvent.Insert }

        // Then - expected new inserted note
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.javaClass?.name).isEqualTo(NotesEvent.Insert::class.java.name)
        val noteId = result?.value?.id ?: -1
        newNote = newNote.copy(
            id = noteId
        )
        Truth.assertThat(result?.value).isEqualTo(newNote)

    }

    @Test
    fun addNote_updateNote_returnUpdatedNote() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase & listen the note events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNotesUseCase.execute().collect { newEvent ->
                events.add(newEvent)
            }
        }


        // When - insert the note
        var newNote = note1.toModel().copy(
            title = "Note title have been updated"
        )
        useCase.execute(newNote)
        val result = events.firstOrNull()
            ?.let { it as NotesEvent.Update }

        // Then - expected new inserted note
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.javaClass?.name).isEqualTo(NotesEvent.Update::class.java.name)
        Truth.assertThat(result?.value).isEqualTo(newNote)

    }

    @Test
    fun addNote_updateANoteWhichNoteAlreadyExist_returnUpdatedNote() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase & listen the note events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNotesUseCase.execute().collect { newEvent ->
                events.add(newEvent)
            }
        }


        // When - update the note which not already exist in rep.
        var newNote = note5.toModel()
        useCase.execute(newNote)
        val result = events.firstOrNull()
            ?.let { it as NotesEvent.Update }

        // Then - expected new inserted note
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.javaClass?.name).isEqualTo(NotesEvent.Update::class.java.name)
        Truth.assertThat(result?.value).isEqualTo(newNote)

    }





}













