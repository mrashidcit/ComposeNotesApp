package com.rashidsaleem.notesapp.feature_addNote.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    dismissButton: () -> Unit,
    confirmButton: () -> Unit,
    ) {

    AlertDialog(
        modifier = modifier
            .semantics {
            contentDescription = "Confirmation Dialog"
        },
        onDismissRequest = { dismissButton() },
        title = {
            Text(
                text = "Alert",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            )
        },
        text = {
            Text(
                text = "Do you really want to delete this note ?",
            )
        },
        dismissButton = {
            Button(
                modifier = Modifier.semantics {
                    contentDescription = "No"
                },
                onClick = dismissButton
            ) {
               Text("No")
            }
        },
        confirmButton = {
            Button(
                modifier = Modifier.semantics {
                    contentDescription = "Yes"
                },
                onClick = confirmButton,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Yes")
            }
        }
    )
}

@Preview
@Composable
private fun ConfirmationDialogPreview() {
    NotesAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            ConfirmationDialog(
                dismissButton = { },
                confirmButton = { },
            )
        }
    }

}