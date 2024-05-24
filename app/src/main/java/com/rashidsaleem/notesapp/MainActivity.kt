package com.rashidsaleem.notesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rashidsaleem.notesapp.core.util.Routes
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.addEditNote.AddEditNoteScreen
import com.rashidsaleem.notesapp.feature.home.presentation.HomeScreen
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.HOME
                    ) {

                        composable(Routes.HOME) {
                            HomeScreen(
                                navigateNext = { route, itemId ->
                                    val targetRoute = "$route/$itemId"
                                    navController.navigate(targetRoute)
                                }
                            )
                        }

                        composable(
                            route = "${Routes.ADD_EDIT_NOTE}/{note_id}",
                            arguments = listOf(
                                navArgument("note_id") { type = NavType.StringType }
                            )
                        ) {
                            AddEditNoteScreen()
                        }

                    }
                }
            }
        }
    }
}
