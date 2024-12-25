package com.rashidsaleem.notesapp.feature_addNote.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_addNote.domain.AddNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.DeleteNoteUseCase
import com.rashidsaleem.notesapp.feature_addNote.domain.GetNoteUseCase
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNoteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var dao: NotesDao
    private lateinit var repository: NotesRepository
    private lateinit var getNoteUseCase: GetNoteUseCase
    private lateinit var addNoteUseCase: AddNoteUseCase
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase
    private lateinit var viewModel: AddNoteViewModel


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
            repository = repository
        )
        addNoteUseCase = AddNoteUseCase(
            repository = repository
        )
        deleteNoteUseCase = DeleteNoteUseCase(
            repository = repository
        )
        viewModel = AddNoteViewModel(
            savedStateHandle = savedStateHandle,
            _getNoteUseCase = getNoteUseCase,
            _addNoteUseCase = addNoteUseCase,
            _deleteNoteUseCase = deleteNoteUseCase,
            ioDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main
        )
    }

    @Test
    fun addNoteScreen_addTitleAndDescription_titleAndDescriptionShouldBeVisible() {

        composeTestRule.setContent {
            AddNoteScreen(
                viewModel = viewModel
            ) {

            }
        }

        val title = "First title using End-to-End test"
        val description = "This is the fastest way to verify features"

        composeTestRule.onNodeWithContentDescription("Enter Title").performTextInput(title)
        composeTestRule.onNodeWithText(title).assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Enter Description")
            .performTextInput(description)
        composeTestRule
            .onNodeWithText(description)
            .assertIsDisplayed()
    }

    @Test
    fun tapOnBackButton_navigateToPreviousScreen() {

        var shouldNavigateBack = false

        composeTestRule.setContent {
            AddNoteScreen(
                viewModel = viewModel,
            ) {
                shouldNavigateBack = true
            }
        }

        composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

        composeTestRule.waitUntil(4000L) {
            shouldNavigateBack
        }

        Truth.assertThat(shouldNavigateBack).isTrue()

    }

    @Test
    fun tapOnDeleteIcon_showConfirmationDialog() {

        composeTestRule.setContent {
            AddNoteScreen(
                viewModel = viewModel
            ) {  }
        }

        composeTestRule
            .onNodeWithContentDescription("Delete Note")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Confirmation Dialog")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("No")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Confirmation Dialog")
            .assertIsNotDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Delete Note")
            .performClick()
        composeTestRule
            .onNodeWithContentDescription("Confirmation Dialog")
            .assertIsDisplayed()
    }

    @Test
    fun deleteANote_navigateToBackScreen() {

        var shouldNavigateBack = false

        composeTestRule.setContent {
            AddNoteScreen(
                viewModel = viewModel
            ) {
                shouldNavigateBack = true
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Delete Note")
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Confirmation Dialog")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Yes")
            .performClick()

        composeTestRule.waitUntil(4000L) {
            shouldNavigateBack
        }

        Truth.assertThat(shouldNavigateBack).isTrue()


    }


}

























