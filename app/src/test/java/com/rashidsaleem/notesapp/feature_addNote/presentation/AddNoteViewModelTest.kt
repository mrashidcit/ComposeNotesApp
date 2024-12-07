package com.rashidsaleem.notesapp.feature_addNote.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.NotesEvent
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_addNote.domain.AddNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.DeleteNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.GetNoteUseCase
import com.rashidsaleem.notesapp.feature_home.presentation.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddNoteViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var dao: NotesDao
    private lateinit var repository: NotesRepository
    private lateinit var getNoteUseCase: GetNoteUseCase
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var listenNotesUseCase: ListenNotesUseCase

    val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {

        savedStateHandle = SavedStateHandle()
        val items = arrayListOf(
            DummyNotesEntity.note1, DummyNotesEntity.note2, DummyNotesEntity.note3
        )
        dao = FakeNotesDao(items)
        repository = NotesRepositoryImpl(
            dao = dao
        )
        getNoteUseCase = GetNoteUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        addNoteUseCase = AddNoteUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        deleteNoteUseCase = DeleteNoteUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )
        listenNotesUseCase = ListenNotesUseCase(
            repository = repository,
            ioDispatcher = UnconfinedTestDispatcher(testDispatcher.scheduler)
        )

    }


    @Test
    fun getNote_provideValidNoteId_returnValidTitleAndDescription() =
        runTest(testDispatcher.scheduler) {

            // Given - setup viewModel
            val noteModel = DummyNotesEntity.note2.toModel()
            savedStateHandle["id"] = noteModel.id
            val viewModel = AddNoteViewModel(
                savedStateHandle = savedStateHandle,
                _getNoteUseCase = getNoteUseCase,
                _addNoteUseCase = addNoteUseCase,
                _deleteNoteUseCase = deleteNoteUseCase,
                ioDispatcher = UnconfinedTestDispatcher(testScheduler),
                mainDispatcher = UnconfinedTestDispatcher(testScheduler)
            )

            // When - getNote method is already call in init block
            val result1Title = viewModel.title.value
            val result2Description = viewModel.description.value

            // Then - expected valid title & description
            Truth.assertThat(result1Title).isEqualTo(noteModel.title)
            Truth.assertThat(result2Description).isEqualTo(noteModel.description)

        }

    @Test
    fun getNote_provideNoNoteId_returnEmptyTitleAndDescription() =
        runTest(testDispatcher.scheduler) {

            // Given - setup viewModel
            savedStateHandle["id"] = -1
            val viewModel = AddNoteViewModel(
                savedStateHandle = savedStateHandle,
                _getNoteUseCase = getNoteUseCase,
                _addNoteUseCase = addNoteUseCase,
                _deleteNoteUseCase = deleteNoteUseCase,
                ioDispatcher = UnconfinedTestDispatcher(testScheduler),
                mainDispatcher = UnconfinedTestDispatcher(testScheduler)
            )

            // When - getNote method is already call in init block
            val result1Title = viewModel.title.value
            val result2Description = viewModel.description.value

            // Then - expected valid title & description
            Truth.assertThat(result1Title).isEmpty()
            Truth.assertThat(result2Description).isEmpty()

        }

    @Test
    fun getNote_provideInvalidNoteId_returnEmptyTitleAndDescription() =
        runTest(testDispatcher.scheduler) {

            // Given - setup viewModel
            savedStateHandle["id"] = 20
            val viewModel = AddNoteViewModel(
                savedStateHandle = savedStateHandle,
                _getNoteUseCase = getNoteUseCase,
                _addNoteUseCase = addNoteUseCase,
                _deleteNoteUseCase = deleteNoteUseCase,
                ioDispatcher = UnconfinedTestDispatcher(testScheduler),
                mainDispatcher = UnconfinedTestDispatcher(testScheduler)
            )

            // When - getNote method is already call in init block
            val result1Title = viewModel.title.value
            val result2Description = viewModel.description.value

            // Then - expected valid title & description
            Truth.assertThat(result1Title).isEmpty()
            Truth.assertThat(result2Description).isEmpty()

        }

    @Test
    fun titleOnValueChange_returnUpdatedTitle() = runTest(testDispatcher.scheduler) {

        // Given - setup viewModel
        savedStateHandle["id"] = -1
        val viewModel = AddNoteViewModel(
            savedStateHandle = savedStateHandle,
            _getNoteUseCase = getNoteUseCase,
            _addNoteUseCase = addNoteUseCase,
            _deleteNoteUseCase = deleteNoteUseCase,
            ioDispatcher = UnconfinedTestDispatcher(testScheduler),
            mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        // When - update the title
        val title = "This is another note"
        viewModel.javaClass.getDeclaredMethod("titleOnValueChange", String::class.java)
            .let { method ->
                method.isAccessible = true
                val params = arrayOfNulls<Any>(1)
                params[0] = title
                method.invoke(viewModel, *params)
            }
        val result = viewModel.title.value

        // Then - expected valid title
        Truth.assertThat(result).isEqualTo(title)
    }

    @Test
    fun descriptionOnValueChange_returnUpdatedDescription() = runTest(testDispatcher.scheduler) {

        // Given - setup viewModel
        savedStateHandle["id"] = -1
        val viewModel = AddNoteViewModel(
            savedStateHandle = savedStateHandle,
            _getNoteUseCase = getNoteUseCase,
            _addNoteUseCase = addNoteUseCase,
            _deleteNoteUseCase = deleteNoteUseCase,
            ioDispatcher = UnconfinedTestDispatcher(testScheduler),
            mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        // When - update the title
        val description = "This is our note description"
        viewModel.javaClass.getDeclaredMethod("descriptionOnValueChange", String::class.java)
            .let { method ->
                method.isAccessible = true
                val params = arrayOfNulls<Any>(1)
                params[0] = description
                method.invoke(viewModel, *params)
            }
        val result = viewModel.description.value

        // Then - expected valid title
        Truth.assertThat(result).isEqualTo(description)
    }

    @Test
    fun backIconOnClick_insertNewNote_returnInsertNoteEventAndNavigateBackEvent() =
        runTest(testDispatcher.scheduler) {

            // Given - setup viewModel , listen NoteEvents, listen AddNoteEvent
            var newNoteModel = DummyNotesEntity.note4.toModel()
            savedStateHandle["id"] = -1
            val viewModel = AddNoteViewModel(
                savedStateHandle = savedStateHandle,
                _getNoteUseCase = getNoteUseCase,
                _addNoteUseCase = addNoteUseCase,
                _deleteNoteUseCase = deleteNoteUseCase,
                ioDispatcher = UnconfinedTestDispatcher(testScheduler),
                mainDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
            val noteEvents = mutableListOf<NotesEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                listenNotesUseCase.execute().collect { newEvent ->
                    noteEvents.add(newEvent)
                }
            }
            val addNoteEvents = mutableListOf<AddNoteEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.event.collect { newEvent ->
                    addNoteEvents.add(newEvent)
                }
            }

            // When - call backIconOnClick
            viewModel.javaClass.getDeclaredMethod("titleOnValueChange", String::class.java)
                .let { method ->
                    method.isAccessible = true
                    val params = arrayOfNulls<Any>(1)
                    params[0] = newNoteModel.title
                    method.invoke(viewModel, *params)
                }
            viewModel.javaClass.getDeclaredMethod("descriptionOnValueChange", String::class.java)
                .let { method ->
                    method.isAccessible = true
                    val params = arrayOfNulls<Any>(1)
                    params[0] = newNoteModel.description
                    method.invoke(viewModel, *params)
                }

            viewModel.javaClass.getDeclaredMethod("backIconOnClick")
                .let { method ->
                    method.isAccessible = true
                    method.invoke(viewModel)
                }

            val result1InsertNoteEvent = noteEvents.lastOrNull()?.let { it as NotesEvent.Insert }
            val result2NavigateBackEvent = addNoteEvents.lastOrNull()
                ?.let { it as AddNoteEvent.NavigateBack }


            // Then - expected InsertNoteEvent & NavigateBackEvent
            Truth.assertThat(result1InsertNoteEvent).isNotNull()
            val noteId = result1InsertNoteEvent?.value?.id ?: -1
            newNoteModel = newNoteModel.copy(
                id = noteId
            )
            Truth.assertThat(result1InsertNoteEvent?.value).isEqualTo(newNoteModel)
            Truth.assertThat(result2NavigateBackEvent).isNotNull()
        }

    @Test
    fun backIconOnClick_updateExistingNote_returnUpdateNoteEventAndNavigateBackEvent() =
        runTest(testDispatcher.scheduler) {

            // Given - setup viewModel , listen NoteEvents, listen AddNoteEvent
            var updatedNoteModel = DummyNotesEntity.note2.toModel().copy(
                title = "Note title have been updated",
                description = "THis is new description"
            )
            savedStateHandle["id"] = updatedNoteModel.id
            val viewModel = AddNoteViewModel(
                savedStateHandle = savedStateHandle,
                _getNoteUseCase = getNoteUseCase,
                _addNoteUseCase = addNoteUseCase,
                _deleteNoteUseCase = deleteNoteUseCase,
                ioDispatcher = UnconfinedTestDispatcher(testScheduler),
                mainDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
            val noteEvents = mutableListOf<NotesEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                listenNotesUseCase.execute().collect { newEvent ->
                    noteEvents.add(newEvent)
                }
            }
            val addNoteEvents = mutableListOf<AddNoteEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.event.collect { newEvent ->
                    addNoteEvents.add(newEvent)
                }
            }

            // When - call backIconOnClick
            viewModel.javaClass.getDeclaredMethod("titleOnValueChange", String::class.java)
                .let { method ->
                    method.isAccessible = true
                    val params = arrayOfNulls<Any>(1)
                    params[0] = updatedNoteModel.title
                    method.invoke(viewModel, *params)
                }
            viewModel.javaClass.getDeclaredMethod("descriptionOnValueChange", String::class.java)
                .let { method ->
                    method.isAccessible = true
                    val params = arrayOfNulls<Any>(1)
                    params[0] = updatedNoteModel.description
                    method.invoke(viewModel, *params)
                }

            viewModel.javaClass.getDeclaredMethod("backIconOnClick")
                .let { method ->
                    method.isAccessible = true
                    method.invoke(viewModel)
                }

            val result1InsertNoteEvent = noteEvents.lastOrNull()?.let { it as NotesEvent.Update }
            val result2NavigateBackEvent = addNoteEvents.lastOrNull()
                ?.let { it as AddNoteEvent.NavigateBack }


            // Then - expected UpdateNoteEvent & NavigateBackEvent
            Truth.assertThat(result1InsertNoteEvent).isNotNull()
            val noteId = result1InsertNoteEvent?.value?.id ?: -1
            updatedNoteModel = updatedNoteModel.copy(
                id = noteId
            )
            Truth.assertThat(result1InsertNoteEvent?.value).isEqualTo(updatedNoteModel)
            Truth.assertThat(result2NavigateBackEvent).isNotNull()
        }


    @Test
    fun hideConfirmationDialog_returnFalse() = runTest(testDispatcher.scheduler) {

        // Given - setup viewModel , listen NoteEvents, listen AddNoteEvent
        savedStateHandle["id"] = -1
        val viewModel = AddNoteViewModel(
            savedStateHandle = savedStateHandle,
            _getNoteUseCase = getNoteUseCase,
            _addNoteUseCase = addNoteUseCase,
            _deleteNoteUseCase = deleteNoteUseCase,
            ioDispatcher = UnconfinedTestDispatcher(testScheduler),
            mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        // When - call hideConfirmationDialog method
        viewModel.javaClass.getDeclaredMethod("hideConfirmationDialog")
            .let { method ->
                method.isAccessible = true
                method.invoke(viewModel)
            }
        val result = viewModel.showConfirmationDialog.value

        // Then - expected false
        Truth.assertThat(result).isEqualTo(false)
    }

    @Test
    fun showConfirmationDialog_returnTrue() = runTest(testDispatcher.scheduler) {

        // Given - setup viewModel , listen NoteEvents, listen AddNoteEvent
        savedStateHandle["id"] = -1
        val viewModel = AddNoteViewModel(
            savedStateHandle = savedStateHandle,
            _getNoteUseCase = getNoteUseCase,
            _addNoteUseCase = addNoteUseCase,
            _deleteNoteUseCase = deleteNoteUseCase,
            ioDispatcher = UnconfinedTestDispatcher(testScheduler),
            mainDispatcher = UnconfinedTestDispatcher(testScheduler)
        )

        // When - call hideConfirmationDialog method
        viewModel.javaClass.getDeclaredMethod("showConfirmationDialog")
            .let { method ->
                method.isAccessible = true
                method.invoke(viewModel)
            }
        val result = viewModel.showConfirmationDialog.value

        // Then - expected false
        Truth.assertThat(result).isEqualTo(true)
    }

    @Test
    fun deleteNote_returnDeleteNoteEventAndFalseAndNavigateBackEvent() =
        runTest(testDispatcher.scheduler) {

            // Given - setup viewModel , listen NoteEvents, listen AddNoteEvent
            val selectedNote = DummyNotesEntity.note3
            savedStateHandle["id"] = selectedNote.id
            val viewModel = AddNoteViewModel(
                savedStateHandle = savedStateHandle,
                _getNoteUseCase = getNoteUseCase,
                _addNoteUseCase = addNoteUseCase,
                _deleteNoteUseCase = deleteNoteUseCase,
                ioDispatcher = UnconfinedTestDispatcher(testScheduler),
                mainDispatcher = UnconfinedTestDispatcher(testScheduler)
            )
            val noteEvents = mutableListOf<NotesEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                listenNotesUseCase.execute().collect { newEvent ->
                    noteEvents.add(newEvent)
                }
            }
            val addNoteEvents = mutableListOf<AddNoteEvent>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.event.collect { newEvent ->
                    addNoteEvents.add(newEvent)
                }
            }

            // When - call deleteNote method
            viewModel.javaClass.getDeclaredMethod("deleteNote")
                .let { method ->
                    method.isAccessible = true
                    method.invoke(viewModel)
                }
            val result1DeleteNoteEvent = noteEvents.lastOrNull()?.let { it as NotesEvent.Delete }
            val result2ShowConfirmationDialog = viewModel.showConfirmationDialog.value
            val result3NavigateBackEvent = addNoteEvents.lastOrNull()
                ?.let { it as AddNoteEvent.NavigateBack }

            // Then - expected false
            Truth.assertThat(result1DeleteNoteEvent).isNotNull()
            val noteId = result1DeleteNoteEvent?.value
            Truth.assertThat(noteId).isEqualTo(selectedNote.id)
            Truth.assertThat(result2ShowConfirmationDialog).isEqualTo(false)
            Truth.assertThat(result3NavigateBackEvent).isNotNull()
        }


}
















