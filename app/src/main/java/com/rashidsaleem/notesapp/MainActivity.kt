package com.rashidsaleem.notesapp

import android.icu.text.MessagePattern.ArgType
import android.os.Bundle
import android.util.Log
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
import com.google.gson.Gson
import com.rashidsaleem.notesapp.addNote.AddNoteScreen
import com.rashidsaleem.notesapp.home.HomeScreen
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

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
                        startDestination = Routes.HOME,
                        navController = navController) {

                        composable(Routes.HOME) {
                            HomeScreen(
                                navigateNext = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }

                        composable(
                                route = Routes.ADD_NOTE + "/{id}",
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

                                    Log.d(TAG, "navigateBack: $it")
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
