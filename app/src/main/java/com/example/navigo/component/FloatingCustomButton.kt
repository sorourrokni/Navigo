package com.example.navigo.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun FloatingCustomButton(
    icon: Int? = null,
    imageVector: ImageVector? = null,
    text: String,
    onClick: () -> Unit = {},
    modifier: Modifier
) {
    Row(modifier = modifier) {
        ExtendedFloatingActionButton(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxWidth(),
            onClick = { onClick() },
            icon = {
                if (imageVector != null) {
                    Icon(
                        imageVector = imageVector,
                        "floating button",
                        tint = MaterialTheme.colorScheme.background,
                    )
                } else if (icon != null) {
                    Icon(
                        painter = painterResource(id = icon),
                        "floating button",
                        tint = MaterialTheme.colorScheme.background,
                    )
                }
            },
            text = { Text(text = text, style = MaterialTheme.typography.titleMedium) },
        )
    }
}