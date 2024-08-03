package com.rashidsaleem.notesapp.addNote

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun AddNote() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // AppBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 20.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                contentDescription = null,
                tint = Color.White,
            )
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = null,
                tint = Color.White,
            )
        }

        TextField(
            value = "This is my title",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Enter Title")
            },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 24.sp
            )
        )

        TextField(
            value = "This is my Description",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ,
            placeholder = {
                Text(text = "Enter Description")
            }
        )

    }

}

@Preview
@Composable
private fun AddNotePreview() {
    NotesAppTheme {
        Surface {
            AddNote()
        }
    }
}