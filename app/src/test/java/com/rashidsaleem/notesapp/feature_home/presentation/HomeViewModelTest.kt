package com.rashidsaleem.notesapp.feature_home.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.Routes
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NoteEntity
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.GetNotesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeNotesDao: FakeNotesDao
    private lateinit var repository: NotesRepository
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var listenNotesUseCase: ListenNotesUseCase
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUpViewModel() {

        val items = arrayListOf<NoteEntity>(
            DummyNotesEntity.note1, DummyNotesEntity.note2, DummyNotesEntity.note3
        )
        fakeNotesDao = FakeNotesDao(items)
        repository = NotesRepositoryImpl(
            dao = fakeNotesDao
        )
        getNotesUseCase = GetNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        listenNotesUseCase = ListenNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        viewModel = HomeViewModel(
            getNotesUseCase = getNotesUseCase,
            listenNotesUseCase = listenNotesUseCase,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler),
            mainDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
    }

    @Test
    fun getNotes_return3Notes() = runTest(testDispatcher.scheduler) {

        // Given - setup the viewModel

        // When - get the notes list
        advanceUntilIdle()
        val result = viewModel.notesList
        println("getNotes_return3Notes - viewModel.notesList = ${viewModel.notesList.size}")

        // Then - expected 3 items
        Truth.assertThat(result.size).isEqualTo(3)

    }

    @Test
    fun listenNotes_insertNewNote_returnNewInsertedNote() = runTest(testDispatcher.scheduler) {

        // Given - setup the ViewModel

        // When - insert a new note
        var newNote = DummyNotesEntity.note4.toModel()
        repository.insert(newNote)
        val result = viewModel.notesList.firstOrNull()

        // Then - expected new inserted note
        Truth.assertThat(result).isNotNull()
        val noteId = result?.id ?: -1
        newNote = newNote.copy(
            id = noteId
        )
        Truth.assertThat(result).isEqualTo(newNote)
    }

    @Test
    fun listenNotes_update2ndNote_returnUpdatedNote() = runTest(testDispatcher.scheduler) {

        // Given - setup the ViewModel

        // When - insert a new note
        advanceUntilIdle()
        var updatedNote = DummyNotesEntity.note2.toModel().copy(
            title = "Note Title have been updated"
        )
        repository.update(updatedNote)
        println("listenNotes_update2ndNote_returnUpdatedNote - viewModel.notesList = ${viewModel.notesList.size}")
        val result = viewModel.notesList.firstOrNull { it.id == updatedNote.id }

        // Then - expected new inserted note
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isEqualTo(updatedNote)
    }

    @Test
    fun listenNotes_delete1stNote_returnNull() = runTest(testDispatcher.scheduler) {

        // Given - setup the ViewModel

        // When - insert a new note
        advanceUntilIdle()
        val noteId = DummyNotesEntity.note1.toModel().id
        repository.delete(noteId)
        val result = viewModel.notesList.firstOrNull { it.id == noteId }

        // Then - expected new inserted note
        Truth.assertThat(result).isNull()
    }

    @Test
    fun addNewNote_returnNavigateNextEvent() = runTest(testDispatcher.scheduler) {

        // Given - setup the ViewModel & listen events
        val events = mutableListOf<HomeEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eventFlow.collect { newEvent ->
                events.add(newEvent)
            }
        }

        // When - call addNewNote
        val method = viewModel.javaClass.getDeclaredMethod("addNewNote")
        method.isAccessible = true
        method.invoke(viewModel)
        val result = events.lastOrNull()?.let { it as HomeEvent.NavigateNext }


        // Then - expected NavigateNextEvent
        Truth.assertThat(result?.javaClass?.name).isEqualTo(HomeEvent.NavigateNext::class.java.name)
        Truth.assertThat(result?.route).isEqualTo(Routes.ADD_NOTE + "/-1")


    }

    @Test
    fun listItemOnClick_returnNavigateNextEvent() = runTest(testDispatcher.scheduler) {

        // Given - setup the ViewModel & listen events
        val events = mutableListOf<HomeEvent>()
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.eventFlow.collect { newEvent ->
                events.add(newEvent)
            }
        }

        // When - call addNewNote
        val noteId = DummyNotesEntity.note2.toModel().id
        val params = arrayOfNulls<Any>(1)
        val method = viewModel.javaClass.getDeclaredMethod("listItemOnClick", Int::class.java)
        params[0] = noteId
        method.isAccessible = true
        method.invoke(viewModel, *params)
        val result = events.lastOrNull()?.let { it as HomeEvent.NavigateNext }

        // Then - expected NavigateNext event
        Truth.assertThat(result?.javaClass?.name).isEqualTo(HomeEvent.NavigateNext::class.java.name)
        val route = Routes.ADD_NOTE + "/$noteId"
        Truth.assertThat(result?.route).isEqualTo(route)
    }




}






















