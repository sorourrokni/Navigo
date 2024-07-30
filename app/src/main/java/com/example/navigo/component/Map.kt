package com.example.navigo.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.carto.graphics.Color
import com.carto.styles.LineStyle
import com.carto.styles.LineStyleBuilder
import com.example.navigo.viewModel.LocationViewModel
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Circle

@Composable
fun Map(locationViewModel: LocationViewModel) {
    val context = LocalContext.current
    val latitude by locationViewModel.latitude.collectAsState()
    val longitude by locationViewModel.longitude.collectAsState()
    val isLocationRetrieved by locationViewModel.isLocationRetrieved.collectAsState()
    var mapView by remember { mutableStateOf<MapView?>(null) }

    LaunchedEffect(Unit) {
        locationViewModel.requestLocationUpdates()
    }

    if (isLocationRetrieved) {
        AndroidView(
            factory = {
                MapView(context).apply {
                    mapView = this
                    // Add the outer lighter blue circle
                    val outerCircle = Circle(
                        LatLng(latitude, longitude),
                        30.0,
                        Color(2, 119, 189, 60),
                        getLineStyle()
                    )
                    addCircle(outerCircle)
                    // Add the inner solid blue circle
                    val innerCircle = Circle(
                        LatLng(latitude, longitude),
                        10.0,
                        Color(2, 119, 189, 190),
                        getLineStyle()
                    )
                    addCircle(innerCircle)
                    moveCamera(LatLng(latitude, longitude), 0f)
                    setZoom(14f, 0f)
                }

            },
            update = { mapView = it }
        )
    } else {
        Text(text = "Retrieving location...", modifier = Modifier.padding(16.dp))
    }
}

private fun getLineStyle(): LineStyle {
    val lineStyleBuilder = LineStyleBuilder()
    lineStyleBuilder.color = Color(0, 0, 0, 0)
    lineStyleBuilder.width = 1f
    return lineStyleBuilder.buildStyle()
}