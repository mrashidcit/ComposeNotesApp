package com.rashidsaleem.notesapp.feature_addNote.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetNoteUseCaseTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNotesDao: NotesDao
    private lateinit var repository: NotesRepository
    private lateinit var useCase: GetNoteUseCase

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
        useCase = GetNoteUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
    }

    @Test
    fun getNote_provideValidNoteId_returnNote() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase

        // When - provide valid note id to get note details
        val noteId = DummyNotesEntity.note3.id ?: -1
        val result = useCase.execute(noteId)

        // Then - expected correct note
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result?.id).isEqualTo(noteId)
    }

    @Test
    fun getNote_provideInvalidNoteId_returnNull() = runTest(testDispatcher.scheduler) {

        // Given - setup the useCase

        // When - provide valid note id to get note details
        val noteId = 30
        val result = useCase.execute(noteId)

        // Then - expected correct note
        Truth.assertThat(result).isNull()
        Truth.assertThat(result?.id).isNotEqualTo(noteId)
    }





}














