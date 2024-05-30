package com.rashidsaleem.notesapp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rashidsaleem.notesapp.core.presentation.components.AppBar
import com.rashidsaleem.notesapp.feature.home.presentation.HomeAction
import com.rashidsaleem.notesapp.feature.home.presentation.HomeUiState
import com.rashidsaleem.notesapp.feature.home.presentation.previewHomeUiState
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun HomeContent(
    uiState: HomeUiState,
    action: (HomeAction) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AppBar()
            ItemsList(
                modifier = Modifier
                    .weight(1f),
                notes = uiState.notes,
                itemOnClick = { note ->
                    action(HomeAction.ItemOnClick(note))
                }
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 16.dp,
                ),
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            onClick = {
                action(HomeAction.AddNewNote)
            },
            ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
    }
}


@Preview
@Composable
fun HomeContentPreview() {
    NotesAppTheme {

        val uiState = previewHomeUiState()

        Surface(
            color = Color.White,
        ) {
            HomeContent(
                uiState = uiState,
                action = { }
            )
        }
    }
}

