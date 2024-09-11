package com.rashidsaleem.notesapp.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rashidsaleem.notesapp.feature_home.presentation.HomeRoutes
import com.rashidsaleem.notesapp.feature_home.presentation.addNote.AddNoteScreen
import com.rashidsaleem.notesapp.feature_home.presentation.home.HomeScreen
import com.rashidsaleem.notesapp.core.presentation.ui.theme.NotesAppTheme

class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity"

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
                        startDestination = HomeRoutes.HOME,
                        navController = navController) {

                        composable(HomeRoutes.HOME) {
                            val newNoteJsonStr = navController
                                .currentBackStackEntry
                                ?.savedStateHandle
                                ?.getStateFlow("new_note", "")
                                ?.collectAsState()

                            HomeScreen(
                                newNote = newNoteJsonStr?.value,
                                navigateNext = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }

                        composable(
                                route = HomeRoutes.ADD_NOTE + "/{id}",
                                arguments = listOf(
                                    navArgument("id") {
                                        this.type = NavType.IntType
                                        this.defaultValue = -1

                                    }
                                )
                            ) {
                            AddNoteScreen(
                                navigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }

                    }
//

                }
            }
        }
    }
}
