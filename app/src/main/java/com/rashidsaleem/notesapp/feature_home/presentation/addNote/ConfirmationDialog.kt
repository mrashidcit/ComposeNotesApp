package com.rashidsaleem.notesapp.feature_home.presentation.addNote

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.rashidsaleem.notesapp.core.presentation.ui.theme.NotesAppTheme

@Composable
fun ConfirmationDialog(
    confirmOnClick: () -> Unit,
    dismissOnClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { dismissOnClick() },
        title = {
                Text(
                    text ="Alert",
                    style = MaterialTheme.typography.titleMedium,
                )
        },
        text = {
            Text(
                text ="Do you really want to delete this note?",
                style = MaterialTheme.typography.bodyMedium,
            )
        },
        confirmButton = {
            Button(
                onClick = confirmOnClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                ),
            ) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = dismissOnClick,
            ) {
                Text(text = "No")
            }
        }

    )
}


@Preview
@Composable
fun ConfirmationDialogPreview() {
    NotesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ConfirmationDialog(
                confirmOnClick = {},
                dismissOnClick = {},
            )
        }
    }
}


