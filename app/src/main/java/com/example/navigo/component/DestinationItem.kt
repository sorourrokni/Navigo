package com.example.navigo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.navigo.data.model.common.Address

@Composable
fun DestinationItem(icon: Int, address: Address) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painterResource(id = icon),
            contentDescription = "location",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)

        )
        address.address?.let {
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .height(48.dp)
                    .width(240.dp)
                    .wrapContentWidth(),
                value = it,
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                textStyle = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.End)
            )
        }
    }
}