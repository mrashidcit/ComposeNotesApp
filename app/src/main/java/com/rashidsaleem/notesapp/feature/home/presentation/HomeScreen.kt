package com.rashidsaleem.notesapp.feature.home.presentation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rashidsaleem.notesapp.feature.home.presentation.components.HomeContent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navigateNext: (String, Int) -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()

    HomeContent(
        uiState = uiState,
        action = viewModel::action
    )

}