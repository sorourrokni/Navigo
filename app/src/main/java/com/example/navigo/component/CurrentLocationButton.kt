package com.example.navigo.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.navigo.R
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView

@Composable
fun CurrentLocationButton(map: MapView, currentLocation: LatLng) {
    FloatingActionButton(
        modifier = Modifier
            .size(56.dp),
        onClick = {
            map.moveCamera(LatLng(currentLocation.latitude, currentLocation.longitude), 0f)
            map.setZoom(16f, 0f)
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.surface,
        shape = CircleShape,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_current_location),
            contentDescription = "current location",
            tint = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding(14.dp)
        )
    }
}