package com.rashidsaleem.notesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.rashidsaleem.notesapp.addNote.AddNoteScreen
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
//                    HomeScreen()
                    AddNoteScreen(
                        navigateBack = {
                            Log.d(TAG, "navigateBack: $it")
                        }
                    )
                }
            }
        }
    }
}
