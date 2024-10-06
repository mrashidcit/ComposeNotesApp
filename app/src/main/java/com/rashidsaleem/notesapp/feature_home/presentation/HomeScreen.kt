package com.rashidsaleem.notesapp.feature_home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navigateNext: (String) -> Unit,
) {

    val notes = viewModel.notesList


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is HomeEvent.NavigateNext -> navigateNext(event.route)
            }
        }
    }

    Scaffold(
        topBar = {
             Row(
                 modifier = Modifier
                     .fillMaxWidth()
                     .background(
                         color = MaterialTheme.colorScheme.primary
                     )
                     .padding(
                         start = 50.dp,
                         top = 20.dp,
                         bottom = 20.dp,
                     )
             ) {
                 
                 Text(
                     text = "Notes App",
                     fontSize = 22.sp,
                     fontWeight = FontWeight.SemiBold,
                     color = Color.White
                 )

             }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.action(HomeAction.AddNewNote)
                },
                containerColor = MaterialTheme.colorScheme.tertiary
                ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = null,
                    tint = Color.White,
                )
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    color = Color.LightGray
                ),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(
                vertical = 18.dp
            )
        ) {

            items(notes.size) { index ->
                val note = notes[index]

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp
                        )
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            viewModel.action(HomeAction.ListItemOnClick(note.id))
                        }
                        .padding(
                            horizontal = 16.dp,
                            vertical = 14.dp,
                        )
                ) {
                    Text(
                        text = note.title,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = note.description,
                    )
                }
            }

        }
    }




}


@Preview
@Composable
private fun HomeScreenPreview() {
    NotesAppTheme {
        Surface(
            color = Color.Gray,
        ) {
            HomeScreen() {

            }
        }
    }

}