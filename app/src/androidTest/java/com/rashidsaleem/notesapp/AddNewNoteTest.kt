package com.rashidsaleem.notesapp

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.domain.DeleteAllNotesUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AddNewNoteTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Inject
    lateinit var deleteAllNotesUseCase: DeleteAllNotesUseCase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @After
    fun onDestroy() = runTest {
        async { deleteAllNotesUseCase.execute() }.await()
    }


    @Test
    fun addOneNewNote_newNoteShouldBeReflectedOnHomeScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeNavigation(navController)
        }

        val newNote = DummyNotesEntity.note1.toModel()
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription("Add New Note").performClick()

        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription("Enter Title")
            .performTextInput(newNote.title)
        composeTestRule.onNodeWithContentDescription("Enter Description")
            .performTextInput(newNote.description)
        composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }
        composeTestRule.waitUntil(6000L) {
            composeTestRule.onNodeWithText(newNote.title).isDisplayed()
        }

        composeTestRule.onNodeWithText(newNote.title).assertExists()
        composeTestRule.onNodeWithText(newNote.description).assertExists()
    }

    @Test
    fun addTwoNewNotes_newNotesShouldBeReflectedOnHomeScreen() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            HomeNavigation(navController)
        }

        val note1 = DummyNotesEntity.note1.toModel()
        val note2 = DummyNotesEntity.note2.toModel()
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }


        note1.let { note ->

            composeTestRule.onNodeWithContentDescription("Add New Note").performClick()

            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            composeTestRule.onNodeWithContentDescription("Enter Title")
                .performTextInput(note.title)
            composeTestRule.onNodeWithContentDescription("Enter Description")
                .performTextInput(note.description)
            composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(6000L) {
                composeTestRule.onNodeWithText(note.title).isDisplayed()
            }

            composeTestRule.onNodeWithText(note.title).assertExists()
            composeTestRule.onNodeWithText(note.description).assertExists()
        }

        note2.let { note ->

            composeTestRule.onNodeWithContentDescription("Add New Note").performClick()
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            composeTestRule.onNodeWithContentDescription("Enter Title")
                .performTextInput(note.title)
            composeTestRule.onNodeWithContentDescription("Enter Description")
                .performTextInput(note.description)
            composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(6000L) {
                composeTestRule.onNodeWithText(note.title).isDisplayed()
            }

            composeTestRule.onNodeWithText(note.title).assertExists()
            composeTestRule.onNodeWithText(note.description).assertExists()
        }

        composeTestRule.onAllNodesWithTag("Note Item").assertCountEquals(2)

    }




}