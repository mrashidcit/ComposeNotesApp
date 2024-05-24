package com.rashidsaleem.notesapp.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rashidsaleem.notesapp.R
import com.rashidsaleem.notesapp.ui.theme.NotesAppTheme

@Composable
fun AppBar(
    title: String = stringResource(id = R.string.notes_app),
    @DrawableRes leadingIcon: Int? = null,
    leadingIconOnClick: (() -> Unit)? = null,
    @DrawableRes trailingIcon: Int? = null,
    trailingIconOnClick: (() -> Unit)? = null,

) {
    Row(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .fillMaxWidth()
            .padding(
                vertical = 10.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier.size(40.dp)
        ) {
            leadingIcon?.let { icon ->
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            leadingIconOnClick?.invoke()
                        }
                    ,
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.weight(1f),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
        )

        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(40.dp)
        ) {
            trailingIcon?.let { icon ->
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .clickable {
                            trailingIconOnClick?.invoke()
                        }
                    ,
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    NotesAppTheme {
        Surface {
            AppBar(
                leadingIcon = R.drawable.baseline_arrow_back_24,
                trailingIcon = R.drawable.baseline_delete_24,
            )
        }
    }
}