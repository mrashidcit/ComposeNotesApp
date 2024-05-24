package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.components.AddEditNoteContent

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    AddEditNoteContent(
        uiState = uiState,
        action = viewModel::action
    )
}