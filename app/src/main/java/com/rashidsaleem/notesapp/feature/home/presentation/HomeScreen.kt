package com.rashidsaleem.notesapp.feature.home.presentation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rashidsaleem.notesapp.feature.home.presentation.components.HomeContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
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