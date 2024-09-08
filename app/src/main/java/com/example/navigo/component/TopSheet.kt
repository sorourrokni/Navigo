package com.example.navigo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.navigo.R
import com.example.navigo.data.model.common.Address

@Composable
fun TopSheet(
    onDismiss: () -> Unit,
    address: Address?,
    carDistanceDetails: Pair<String, String>?,
    motorcycleDistanceDetails: Pair<String, String>?,
    onMultiDestinationRouting: () -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back icon",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(24.dp)
                    .height(24.dp)
                    .clickable { onDismiss() }
            )
            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        painterResource(id = R.drawable.ic_circle_dot),
                        contentDescription = "location",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "location",
                        tint = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.8f),
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Icon(
                        painterResource(id = R.drawable.ic_marker_bold),
                        contentDescription = "location",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                }
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                            .height(48.dp)
                            .width(240.dp)
                            .wrapContentWidth(),
                        value = "لوکیشن فعلی",
                        onValueChange = {},
                        singleLine = true,
                        readOnly = true,
                        textStyle = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.End)
                    )
                    if (address != null) {
                        address.address?.let {
                            OutlinedTextField(
                                modifier = Modifier
                                    .padding(start = 8.dp, top = 8.dp)
                                    .height(48.dp)
                                    .width(240.dp)
                                    .wrapContentWidth(),
                                value = it,
                                singleLine = true,
                                onValueChange = {},
                                readOnly = true,
                                textStyle = MaterialTheme.typography.bodySmall.copy(textAlign = TextAlign.End)
                            )
                        }
                    }
                }

            }
            Row {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "more icon",
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .width(24.dp)
                        .height(24.dp)
                        .clickable { expanded = !expanded }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.clip(RoundedCornerShape(8.dp))
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                        onClick = { onMultiDestinationRouting() },
                        text = {
                            Text(
                                text = "افزودن مقصد",
                                style = MaterialTheme.typography.titleSmall
                            )
                        })
                }
            }
        }
        ToggleButtons(
            carDistanceDetails = carDistanceDetails,
            motorcycleDistanceDetails = motorcycleDistanceDetails
        )
    }
}