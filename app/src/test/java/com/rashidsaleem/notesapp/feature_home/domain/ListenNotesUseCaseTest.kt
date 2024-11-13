package com.rashidsaleem.notesapp.feature_home.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NoteEntity
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.NotesEvent
import com.rashidsaleem.notesapp.core.domain.models.NoteModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
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
class ListenNotesUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var items: ArrayList<NoteEntity>
    private lateinit var itemModels: List<NoteModel>
    private lateinit var fakeNotesDao: FakeNotesDao
    private lateinit var repository: NotesRepositoryImpl
    private lateinit var useCase: ListenNotesUseCase

    private var testDispatcher = StandardTestDispatcher()

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
        useCase = ListenNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
    }

    @Test
    fun listenNotes_insertANoteNote_returnInsertNoteEvent() = runTest(testDispatcher.scheduler) {

        // Given - start listing the events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            useCase.execute().collect { newEvent ->
                events.add(newEvent)
            }
        }

        // When - insert a new note
        var newNote = note4.toModel()
        val noteId = repository.insert(newNote)
        newNote = newNote.copy(
            id = noteId
        )
        val result = events.firstOrNull()

        // Then - expected insert note event
        Truth.assertThat((result as NotesEvent.Insert).javaClass.simpleName)
            .isEqualTo(NotesEvent.Insert::class.java.simpleName)
        Truth.assertThat((result as NotesEvent.Insert).value)
            .isEqualTo(newNote)
    }

    @Test
    fun listenNotes_updateOneNote_returnUpdateNoteEvent() = runTest(testDispatcher.scheduler) {

        // Given - start listing the events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            useCase.execute().collect { newEvent ->
                events.add(newEvent)
            }
        }

        // When - update one note
        val updatedNote = note1.toModel().copy(
            title = "Note title updated"
        )
        repository.update(updatedNote)
        val result = events.firstOrNull()?.let {
            it as NotesEvent.Update
        }

        // Then - expecting Update note event
        Truth.assertThat(result?.javaClass?.simpleName).isEqualTo(NotesEvent.Update::class.java.simpleName)
        Truth.assertThat(result?.value).isEqualTo(updatedNote)

    }

    @Test
    fun listenNotes_deleteOneNote_returnDeleteNoteEvent() = runTest(testDispatcher.scheduler) {

        // Given - start listing the events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            useCase.execute().collect { newEvent ->
                events.add(newEvent)
            }
        }

        // When - delete one note
        val noteId = note1.id ?: -1
        repository.delete(noteId)
        val result = events.firstOrNull()
            ?.let { it as NotesEvent.Delete }

        // Then - expecting delete note event
        Truth.assertThat(result?.javaClass?.simpleName).isEqualTo(NotesEvent.Delete::class.java.simpleName)
        Truth.assertThat(result?.value).isEqualTo(noteId)

    }



}

















