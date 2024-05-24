package com.rashidsaleem.notesapp.feature.addEditNote.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.core.presentation.components.AppBar
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.AddEditNoteAction
import com.rashidsaleem.notesapp.feature.addEditNote.presentation.AddEditNoteUiState
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun AddEditNoteContent(
    uiState: AddEditNoteUiState,
    action: (AddEditNoteAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
            )
    ) {
        AppBar(
            title = "",
            leadingIcon = R.drawable.baseline_arrow_back_24,
            trailingIcon = R.drawable.baseline_delete_24,
            leadingIconOnClick = {
                
            },
            trailingIconOnClick = {

            }
        )
        TextField(
            value = "Abc 123",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.headlineMedium,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
            )
        )
        TextField(
            value = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged.",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ,
            textStyle = MaterialTheme.typography.bodyMedium,
        )


    }
}


@Preview
@Composable
private fun AddEditNoteContentPreview() {
    NotesAppTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            AddEditNoteContent()
        }
    }
}