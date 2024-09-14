package com.example.navigo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.navigo.R
import com.example.navigo.data.model.common.Address

@Composable
fun DynamicTopSheet(
    onDismiss: () -> Unit,
    address: Address?,
    carDistanceDetails: Pair<String, String>?,
    motorcycleDistanceDetails: Pair<String, String>?,
    navController: NavHostController

) {

    var isAddingDestination by remember { mutableStateOf(false) }
    val destinations =
        remember { mutableStateListOf("لوکیشن فعلی", address?.address ?: "مقصد اولیه ") }

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
            Icon(
                Icons.Filled.Add,
                contentDescription = "add icon",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .width(24.dp)
                    .height(24.dp)
                    .clickable {
                        destinations.add("آدرس جدید")
                        isAddingDestination = true
                    }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(destinations.size) { index ->
                val icon: Int = when (index) {
                    0 -> R.drawable.ic_circle_dot
                    1 -> R.drawable.ic_marker_bold
                    else -> R.drawable.ic_marker_bold
                }
                DestinationItem(
                    address = destinations[index],
                    icon = icon
                )
            }
        }
        ToggleButtons(
            carDistanceDetails = carDistanceDetails,
            motorcycleDistanceDetails = motorcycleDistanceDetails
        )
    }
}