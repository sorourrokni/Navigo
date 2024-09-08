package com.example.navigo.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.navigo.R

@Composable
fun ToggleButtons(
    carDistanceDetails: Pair<String, String>?,
    motorcycleDistanceDetails: Pair<String, String>?,
) {
    var selectedButton by remember { mutableIntStateOf(0) }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center.also { Arrangement.spacedBy(8.dp) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Button(
            onClick = { selectedButton = 0 },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton == 0) MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ) else MaterialTheme.colorScheme.background,
                contentColor = if (selectedButton == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.ic_car_bold),
                contentDescription = "car type"
            )
            if (carDistanceDetails != null) {
                Text(carDistanceDetails.second)
            }
        }
        Button(
            onClick = { selectedButton = 1 },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton == 1) MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ) else MaterialTheme.colorScheme.background,
                contentColor = if (selectedButton == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.ic_motorcycle),
                contentDescription = "motorcycle type"
            )
            if (motorcycleDistanceDetails != null) {
                Text(motorcycleDistanceDetails.second)
            }
        }
        Button(
            onClick = { selectedButton = 2 },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedButton == 2) MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ) else MaterialTheme.colorScheme.background,
                contentColor = if (selectedButton == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary
            ),
        ) {
            Icon(
                modifier = Modifier
                    .width(24.dp)
                    .height(24.dp)
                    .padding(end = 8.dp),
                painter = painterResource(id = R.drawable.walk),
                contentDescription = "walk type"
            )
            Text("_")
        }
    }
}

