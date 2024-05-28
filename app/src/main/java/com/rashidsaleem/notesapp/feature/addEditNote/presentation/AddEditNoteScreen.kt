package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.components.AddEditNoteContent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                AddEditNoteEvent.NavigateBack -> navigateBack()
            }
        }
    }

    AddEditNoteContent(
        uiState = uiState,
        action = viewModel::action
    )
}