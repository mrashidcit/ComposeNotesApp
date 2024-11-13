package com.rashidsaleem.notesapp.feature_addNote.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.NotesEvent
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteNoteUseCaseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNotesDao: NotesDao
    private lateinit var repository: NotesRepository
    private lateinit var useCase: DeleteNoteUseCase
    private lateinit var listenNotesUseCase: ListenNotesUseCase

    private var testDispatcher = StandardTestDispatcher()

    @Before
    fun setUpUseCase() {
        val items = arrayListOf(
            DummyNotesEntity.note1,
            DummyNotesEntity.note2,
            DummyNotesEntity.note3,
        )
        fakeNotesDao = FakeNotesDao(items)
        repository = NotesRepositoryImpl(
            dao = fakeNotesDao
        )
        useCase = DeleteNoteUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        listenNotesUseCase = ListenNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
    }

    @Test
    fun deleteNote_provideValidNoteId_returnDeletedNoteId() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase & listen note events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNotesUseCase.execute().collect{ newEvent ->
                events.add(newEvent)
            }
        }

        // When - provide valid delete not id to delete the note
        val noteId = DummyNotesEntity.note1.id ?: -1
        useCase.execute(noteId)
        val result = events.firstOrNull()
            ?.let { it as NotesEvent.Delete }


        // Then - expected deleted note id
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.value).isEqualTo(noteId)

    }

    @Test
    fun deleteNote_provideInvalidNoteId_returnDeletedNoteId() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase & listen note events
        val events = mutableListOf<NotesEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNotesUseCase.execute().collect{ newEvent ->
                events.add(newEvent)
            }
        }

        // When - provide valid delete not id to delete the note
        val noteId = 30
        useCase.execute(noteId)
        val result = events.firstOrNull()
            ?.let { it as NotesEvent.Delete }


        // Then - expected deleted note id
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.value).isEqualTo(noteId)

    }





}










