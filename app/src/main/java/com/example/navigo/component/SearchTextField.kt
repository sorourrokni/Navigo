package com.example.navigo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField() {
    TextField(
        readOnly = true,
        value = "کجا میخوای بری؟",
        onValueChange = {},
        trailingIcon = {
            Icon(Icons.Filled.Search, contentDescription = "Search", tint = Color(0, 52, 89))
        },
        modifier = Modifier
            .clickable {
                //go to search screen
            }
            .background(color = Color.Transparent)
            .padding(all = 8.dp)
            .height(56.dp)
            .clip(shape = CircleShape)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color(231, 236, 239),
            unfocusedContainerColor = Color(231, 236, 239),
        ),
        textStyle = TextStyle(textAlign = TextAlign.End, color = Color(0, 52, 89)),
    )
}
