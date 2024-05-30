package com.rashidsaleem.notesapp.feature.addEditNote.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.core.util.extensions.showToast
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.components.AddEditNoteContent

@Composable
fun AddEditNoteScreen(
    viewModel: AddEditNoteViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navigateBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.event.collect { event ->
            when (event) {
                AddEditNoteEvent.NavigateBack -> navigateBack()
                is AddEditNoteEvent.ShowToast -> context.showToast(
                    message = context.getString(R.string.successfully_deleted)
                )
            }
        }
    }

    AddEditNoteContent(
        uiState = uiState,
        action = viewModel::action
    )
}