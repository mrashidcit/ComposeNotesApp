package com.rashidsaleem.notesapp

import android.icu.text.CaseMap.Title
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.printToLog
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
class EditNoteTest {

    private val TAG = "EditNoteTest"

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltComponentActivity>()

    @Inject
    lateinit var addNoteUseCase: AddNoteUseCase

    @Inject
    lateinit var deleteAllNotesUseCase: DeleteAllNotesUseCase

    @Inject
    lateinit var listenNotesUseCase: ListenNotesUseCase

    val note1 = DummyNotesEntity.note1.toModel().copy(
        id = -1,
    )
    val note2 = DummyNotesEntity.note2.toModel().copy(
        id = -1,
    )
    var note1Id = 1
    var note2Id = 2

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runTest {
        hiltRule.inject()

        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            listenNotesUseCase.execute().collect { event ->
                when (event) {
                    is NotesEvent.Insert -> {
                        when {
                            (event.value.title.equals(note1.title)) -> {
                                note1Id = event.value.id
                            }
                            (event.value.title.equals(note2.title)) -> {
                                note2Id = event.value.id
                            }

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
    fun editOneNote_updatedNoteShouldBeReflectedInHomeScreenListing() {

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

        // NoteDetailsScreen , Update Title & Description, Tap on Back button
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
        }
        val updatedNote = note1.copy(
            title = "Note 1 title have been updated",
            description = "This is the details about how we can do this through test"
        )
        composeTestRule.onNodeWithContentDescription("Enter Title").apply {
            performTextClearance()
            performTextInput(updatedNote.title)
        }
        composeTestRule.onNodeWithContentDescription("Enter Description").apply {
            performTextClearance()
            performTextInput(updatedNote.description)
        }
        composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

        // Landed back on HomeScreen, Verify updated note in the Listing
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText("Notes App").isDisplayed()
        }
        composeTestRule.waitUntil(4000L) {
            composeTestRule.onNodeWithText(updatedNote.title).isDisplayed()
        }
        composeTestRule.onNodeWithText(updatedNote.title).assertExists()
        composeTestRule.onNodeWithText(updatedNote.description).assertExists()

    }


    @Test
    fun edit1stNoteTwoTimes_updatedNoteShouldBeReflectedInHomeScreenListing() {

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

            // NoteDetailsScreen , Update Title & Description, Tap on Back button
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            val updatedNote = note.copy(
                title = "Note 1 title have been updated",
                description = "This is the details about how we can do this through test"
            )
            composeTestRule.onNodeWithContentDescription("Enter Title").apply {
                performTextClearance()
                performTextInput(updatedNote.title)
            }
            composeTestRule.onNodeWithContentDescription("Enter Description").apply {
                performTextClearance()
                performTextInput(updatedNote.description)
            }
            composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

            // Landed back on HomeScreen, Verify updated note in the Listing
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(updatedNote.title).isDisplayed()
            }
            composeTestRule.onNodeWithText(updatedNote.title).assertExists()
            composeTestRule.onNodeWithText(updatedNote.description).assertExists()
        }

        note1.let { note ->

            composeTestRule.onNodeWithContentDescription(note1Id.toString()).performClick()

            // NoteDetailsScreen , Update Title & Description, Tap on Back button
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            val updatedNote = note.copy(
                title = "Note 1 title have been updated - 2nd time",
                description = "This is the details about how we can do this through test - 2nd time"
            )
            composeTestRule.onNodeWithContentDescription("Enter Title").apply {
                performTextClearance()
                performTextInput(updatedNote.title)
            }
            composeTestRule.onNodeWithContentDescription("Enter Description").apply {
                performTextClearance()
                performTextInput(updatedNote.description)
            }
            composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

            // Landed back on HomeScreen, Verify updated note in the Listing
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(updatedNote.title).isDisplayed()
            }
            composeTestRule.onNodeWithText(updatedNote.title).assertExists()
            composeTestRule.onNodeWithText(updatedNote.description).assertExists()
        }

    }

    @Test
    fun editTwoNotesOneByOne_updatedNotesShouldBeReflectedInHomeScreenListing() {

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
            composeTestRule.onRoot().printToLog(TAG)
            composeTestRule.onNodeWithContentDescription(note1Id.toString()).performClick()

            // NoteDetailsScreen , Update Title & Description, Tap on Back button
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            val updatedNote = note.copy(
                title = "Note 1 title have been updated",
                description = "This is the details about how we can do this through test"
            )
            composeTestRule.onNodeWithContentDescription("Enter Title").apply {
                performTextClearance()
                performTextInput(updatedNote.title)
            }
            composeTestRule.onNodeWithContentDescription("Enter Description").apply {
                performTextClearance()
                performTextInput(updatedNote.description)
            }
            composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

            // Landed back on HomeScreen, Verify updated note in the Listing
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(updatedNote.title).isDisplayed()
            }
            composeTestRule.onNodeWithText(updatedNote.title).assertExists()
            composeTestRule.onNodeWithText(updatedNote.description).assertExists()
        }


        note2.let { note ->
            composeTestRule.onNodeWithContentDescription(note2Id.toString()).performClick()

            // NoteDetailsScreen , Update Title & Description, Tap on Back button
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithContentDescription("AddNoteScreen").isDisplayed()
            }
            val updatedNote = note.copy(
                title = "Note 2 title have been updated",
                description = "This is the details about how we can do this through test - 2nd note"
            )
            composeTestRule.onNodeWithContentDescription("Enter Title").apply {
                performTextClearance()
                performTextInput(updatedNote.title)
            }
            composeTestRule.onNodeWithContentDescription("Enter Description").apply {
                performTextClearance()
                performTextInput(updatedNote.description)
            }
            composeTestRule.onNodeWithContentDescription("Navigate Back").performClick()

            // Landed back on HomeScreen, Verify updated note in the Listing
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText("Notes App").isDisplayed()
            }
            composeTestRule.waitUntil(4000L) {
                composeTestRule.onNodeWithText(updatedNote.title).isDisplayed()
            }
            composeTestRule.onNodeWithText(updatedNote.title).assertExists()
            composeTestRule.onNodeWithText(updatedNote.description).assertExists()
        }

    }

}







