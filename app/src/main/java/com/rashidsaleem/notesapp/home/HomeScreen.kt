package com.rashidsaleem.notesapp.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun HomeScreen() {

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
                onClick = { /*TODO*/ },
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

            items(20) {
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
                        .padding(
                            horizontal = 16.dp,
                            vertical = 14.dp,
                        )
                ) {
                    Text(
                        text = "test 2 - 1234",
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "another day",
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
            HomeScreen()
        }
    }

}