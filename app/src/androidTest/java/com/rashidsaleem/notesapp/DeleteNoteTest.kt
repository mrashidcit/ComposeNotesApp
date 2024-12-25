package com.rashidsaleem.notesapp

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.rashidsaleem.notesapp.core.data.local.DummyNotesEntity
import com.rashidsaleem.notesapp.core.data.local.toModel
import com.rashidsaleem.notesapp.core.domain.DeleteAllNotesUseCase
import com.rashidsaleem.notesapp.core.domain.ListenNotesUseCase
import com.rashidsaleem.notesapp.core.domain.NotesEvent
import com.rashidsaleem.notesapp.feature_addNote.domain.AddNoteUseCase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DeleteNoteTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Inject
    lateinit var addNoteUseCase: AddNoteUseCase

    @Inject
    lateinit var listenNoteUseCase: ListenNotesUseCase

    @Inject
    lateinit var deleteAllNotesUseCase: DeleteAllNotesUseCase


    private val note1 = DummyNotesEntity.note1.toModel().copy(
        id = -1
    )
    private val note2 = DummyNotesEntity.note2.toModel().copy(
        id = -1
    )
    private var note1Id: Int = 0
    private var note2Id: Int = 0


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runTest {

        hiltRule.inject()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNoteUseCase.execute().collect { event ->
                when (event) {
                    is NotesEvent.Insert -> {
                        if (event.value.title == note1.title) {
                            note1Id = event.value.id
                        } else if (event.value.title == note2.title) {
                            note2Id = event.value.id
                        }
                    }

                    is NotesEvent.Delete -> Unit
                    is NotesEvent.Update -> Unit
                }
            }
        }

        async { addNoteUseCase.execute(note1) }.await()
        async { addNoteUseCase.execute(note2) }.await()

    }

    @After
    fun onDestroy() = runTest {
        async { deleteAllNotesUseCase.execute() }.await()
    }


    @Test
    fun deleteOneNote_deletedNoteShouldBeRemovedFromHomeScreenListing() {

        composeTestRule.setContent {

            val navController = rememberNavController()
            HomeNavigation(navController)

        }

        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText(note1.title).isDisplayed()
        }
        composeTestRule.onNodeWithContentDescription(note1Id.toString()).performClick()

        // NoteDetail Screen, Verify Title & Description, Tap Delete Icon, Tap on 'Yes' button
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
        }
        composeTestRule.onNodeWithText(note1.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(note1.description).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Delete Note").performClick()
        composeTestRule.onNodeWithContentDescription("Yes").performClick()

        // HomeScreen, Wait for Item removal

        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText(note1.title).isNotDisplayed()
        }
        composeTestRule.onNodeWithText(note1.title).assertDoesNotExist()
        composeTestRule.onNodeWithText(note1.description).assertDoesNotExist()
        composeTestRule.onAllNodesWithTag("Note Item").assertCountEquals(1)

    }

    @Test
    fun deleteTwoNotes_deletedNoteShouldBeRemovedFromHomeScreenListing() {

        composeTestRule.setContent {

            val navController = rememberNavController()
            HomeNavigation(navController)

        }

        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }

        note1.let { note ->
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(note.title).isDisplayed()
            }
            composeTestRule.onNodeWithContentDescription(note1Id.toString()).performClick()

            // NoteDetail Screen, Verify Title & Description, Tap Delete Icon, Tap on 'Yes' button
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            composeTestRule.onNodeWithText(note.title).assertIsDisplayed()
            composeTestRule.onNodeWithText(note.description).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Delete Note").performClick()
            composeTestRule.onNodeWithContentDescription("Yes").performClick()

            // HomeScreen, Wait for Item removal

            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(note.title).isNotDisplayed()
            }
            composeTestRule.onNodeWithText(note.title).assertDoesNotExist()
            composeTestRule.onNodeWithText(note.description).assertDoesNotExist()
            composeTestRule.onAllNodesWithTag("Note Item").assertCountEquals(1)
        }

        note2.let { note ->
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(note.title).isDisplayed()
            }
            composeTestRule.onNodeWithContentDescription(note2Id.toString()).performClick()

            // NoteDetail Screen, Verify Title & Description, Tap Delete Icon, Tap on 'Yes' button
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            composeTestRule.onNodeWithText(note.title).assertIsDisplayed()
            composeTestRule.onNodeWithText(note.description).assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Delete Note").performClick()
            composeTestRule.onNodeWithContentDescription("Yes").performClick()

            // HomeScreen, Wait for Item removal

            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(note.title).isNotDisplayed()
            }
            composeTestRule.onNodeWithText(note.title).assertDoesNotExist()
            composeTestRule.onNodeWithText(note.description).assertDoesNotExist()
            composeTestRule.onAllNodesWithTag("Note Item").assertCountEquals(0)
        }

    }




}
















