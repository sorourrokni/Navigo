package com.example.navigo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.navigo.R

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onMicClick: () -> Unit,
    navController: NavHostController
) {
    TextField(
        value = query,
        onValueChange = onQueryChange,
        trailingIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.Filled.ArrowForward,
                    contentDescription = "Back Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        leadingIcon = {
            IconButton(onClick = { onMicClick() }) {
                Icon(
                    painterResource(id = R.drawable.ic_microphone),
                    contentDescription = "Mic Icon",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        placeholder = {
            Text(
                text = "جستجو",
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDirection = TextDirection.Rtl,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, end = 24.dp, start = 24.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colorScheme.background),
        textStyle = MaterialTheme.typography.bodySmall.copy(
            textDirection = TextDirection.Rtl,
            textAlign = TextAlign.Start
        ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}