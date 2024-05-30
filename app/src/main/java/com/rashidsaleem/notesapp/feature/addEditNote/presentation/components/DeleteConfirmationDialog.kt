package com.rashidsaleem.notesapp.feature.addEditNote.presentation.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme
import com.rashidsaleem.notesapp.ui.theme.Typography

@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit,
    confirmOnClick: () -> Unit,
    dismissOnClick: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(id = R.string.alert),
                style = Typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.do_you_really_want_to_delete_this_note),
                style = Typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSecondary
                ),
            )
        },
        containerColor = MaterialTheme.colorScheme.secondary,
        titleContentColor = MaterialTheme.colorScheme.onSecondary,
        textContentColor = MaterialTheme.colorScheme.onSecondary,
        confirmButton = {
            Button(
                onClick = { confirmOnClick() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError,
                ),
            ) {
                Text(
                    text = "Yes",
                )
            }
        },
        dismissButton = {
            Button(onClick = { dismissOnClick() }) {
                Text(
                    text = "No",
                )
            }
        },
    )

}

@Preview
@Composable
private fun DeleteConfirmationDialogPreview() {
    NotesAppTheme {
        Surface(
            color = Color.Gray,
            modifier = Modifier.fillMaxSize()
        ) {
            DeleteConfirmationDialog(
                onDismiss = {},
                confirmOnClick = {},
                dismissOnClick = {},
            )
        }
    }
}