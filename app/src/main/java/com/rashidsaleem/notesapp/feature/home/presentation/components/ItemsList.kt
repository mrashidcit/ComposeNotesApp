package com.rashidsaleem.notesapp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rashidsaleem.notesapp.feature.home.domain.models.Note
import com.rashidsaleem.notesapp.feature.home.domain.models.dummyNotes
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun ItemsList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    itemOnClick: (Note) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        )
    ) {
        items(notes) { item ->
            NoteItem(item) {
                itemOnClick(item)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
private fun ItemsListPreview() {
    NotesAppTheme {

        val notes = remember {
            dummyNotes()
        }

        Surface(
            color = Color.Gray,
            modifier = Modifier.fillMaxSize()
        ) {
            ItemsList(
                modifier = Modifier.fillMaxSize(),
                notes = notes) {

            }
        }
    }
}