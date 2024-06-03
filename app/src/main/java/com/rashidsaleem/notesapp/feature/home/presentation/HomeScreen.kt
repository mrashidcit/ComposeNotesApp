package com.rashidsaleem.notesapp.feature.home.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.rashidsaleem.notesapp.MainActivity
import com.rashidsaleem.notesapp.NotesApp
import com.rashidsaleem.notesapp.feature.home.presentation.components.HomeContent
import kotlinx.coroutines.flow.collectLatest
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    activity: MainActivity = LocalContext.current as MainActivity,
    viewModel: HomeViewModel = viewModel {
        HomeViewModel(NotesApp.repository)
    },
    navigateNext: (String, Int) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is HomeEvent.NavigateNext -> navigateNext(event.route, event.noteId)
            }
        }
    }

    HomeContent(
        uiState = uiState,
        action = viewModel::action
    )

}