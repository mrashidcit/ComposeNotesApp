package com.rashidsaleem.notesapp.feature_home.presentation

import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.google.common.truth.Truth
import com.rashidsaleem.notesapp.Routes
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.FakeNotesDao
import com.rashidsaleem.notesapp.core.data.local.NotesDao
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.data.respository.NotesRepositoryImpl
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.repository.NotesRepository
import com.rashidsaleem.notesapp.feature_home.domain.GetNotesUseCase
import kotlinx.coroutines.Dispatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var dao: NotesDao
    private lateinit var repository: NotesRepository
    private lateinit var getNotesUseCase: GetNotesUseCase
    private lateinit var listenNotesUseCase: ListenNotesUseCase
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUpViewModel() {

        val items = arrayListOf(
            DummyNotesEntity.note1, DummyNotesEntity.note2, DummyNotesEntity.note3
        )
        dao = FakeNotesDao(items)
        repository = NotesRepositoryImpl(
            dao = dao,
        )
        getNotesUseCase = GetNotesUseCase(
            repository = repository
        )
        listenNotesUseCase = ListenNotesUseCase(
            repository = repository
        )
        viewModel = HomeViewModel(
            getNotesUseCase = getNotesUseCase,
            listenNotesUseCase = listenNotesUseCase,
            ioDispatcher = Dispatchers.IO,
            mainDispatcher = Dispatchers.Main,
        )
    }

    @Test
    fun homeScreen_verifyScreenTitleAndFabButton() {

        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel
            ) {  }
        }


        composeTestRule
            .onNodeWithText("Notes App")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Add New Note")
            .assertIsDisplayed()

    }

    @Test
    fun homeScreen_getNotes_threeNotesItemsInTheList() {

        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel
            ) {

            }
        }

        val note1 = DummyNotesEntity.note1.toModel()
        val note2 = DummyNotesEntity.note2.toModel()
        val note3 = DummyNotesEntity.note3.toModel()

        composeTestRule.waitUntil(4000L) {
            composeTestRule
                .onNodeWithText(note1.title)
                .isDisplayed()
        }

        note1.let { note ->
            composeTestRule
                .onNodeWithText(note.title)
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(note.description)
                .assertIsDisplayed()
        }

        note2.let { note ->
            composeTestRule
                .onNodeWithText(note.title)
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(note.description)
                .assertIsDisplayed()
        }

        note3.let { note ->
            composeTestRule
                .onNodeWithText(note.title)
                .assertIsDisplayed()
            composeTestRule
                .onNodeWithText(note.description)
                .assertIsDisplayed()
        }

    }

    @Test
    fun homeScreen_tapOnFabButton_navigateToNextScreenToAddNewNote() {

        var navigateNextRoute = ""

        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel
            ) { route ->
                navigateNextRoute = route
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Add New Note")
            .performClick()


        Truth.assertThat(navigateNextRoute).isEqualTo(Routes.ADD_NOTE + "/-1")

    }

    @Test
    fun homeScreen_tapOnNoteListItem_selectedNoteDetailOnNextScreen() {

        var navigateNextRoute = ""

        composeTestRule.setContent {
            HomeScreen(
                viewModel = viewModel
            ) { route ->
                navigateNextRoute = route
            }
        }

        val note1 = DummyNotesEntity.note1.toModel()
        val note2 = DummyNotesEntity.note2.toModel()
        val note3 = DummyNotesEntity.note3.toModel()

        composeTestRule.waitUntil(4000L) {
            composeTestRule
                .onNodeWithContentDescription(note1.id.toString())
                .isDisplayed()
        }

        note1.let { note ->
            composeTestRule
                .onNodeWithContentDescription(note.id.toString())
                .performClick()

            composeTestRule.mainClock.advanceTimeBy(2000L)

            Truth.assertThat(navigateNextRoute).isEqualTo(Routes.ADD_NOTE + "/${note.id}")
        }

        note2.let { note ->
            composeTestRule
                .onNodeWithContentDescription(note.id.toString())
                .performClick()

            composeTestRule.mainClock.advanceTimeBy(2000L)

            Truth.assertThat(navigateNextRoute).isEqualTo(Routes.ADD_NOTE + "/${note.id}")
        }

        note3.let { note ->
            composeTestRule
                .onNodeWithContentDescription(note.id.toString())
                .performClick()

            composeTestRule.mainClock.advanceTimeBy(2000L)

            Truth.assertThat(navigateNextRoute).isEqualTo(Routes.ADD_NOTE + "/${note.id}")
        }




    }


}




















