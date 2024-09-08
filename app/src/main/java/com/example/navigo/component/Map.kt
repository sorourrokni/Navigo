package com.example.navigo.component

import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.carto.graphics.Color
import com.carto.styles.LineStyleBuilder
import com.example.navigo.viewModel.LocationViewModel
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Circle
import org.neshan.mapsdk.model.Marker
import org.neshan.mapsdk.model.Polyline


@Composable
fun Map(
    locationViewModel: LocationViewModel,
    onMapLongClick: (LatLng) -> Unit,
    onSearchClick: () -> Unit,
    routePolyline: List<LatLng> = emptyList(),
    searchSelectedLocation: LatLng? = null,
    multiDestinationMode: Boolean = false,
) {
    val context = LocalContext.current
    val latitude by locationViewModel.latitude.collectAsState()
    val longitude by locationViewModel.longitude.collectAsState()
    val isLocationRetrieved by locationViewModel.isLocationRetrieved.collectAsState()
    var previousLatitude by remember { mutableDoubleStateOf(latitude) }
    var previousLongitude by remember { mutableDoubleStateOf(longitude) }
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var markers by remember { mutableStateOf<List<Marker>>(emptyList()) }
    var outerCircle by remember { mutableStateOf<Circle?>(null) }
    var innerCircle by remember { mutableStateOf<Circle?>(null) }
    var polyline by remember { mutableStateOf<Polyline?>(null) }
    var previousDestination by remember { mutableStateOf<LatLng?>(null) }
    var isMultiDestination by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        locationViewModel.requestLocationUpdates()
    }
    LaunchedEffect(searchSelectedLocation) {
        if (searchSelectedLocation != null) {
            mapView?.let {
                markers = handleMarkers(
                    mapView,
                    markers,
                    searchSelectedLocation,
                    false,
                    context
                )
            }
        }
    }
    LaunchedEffect(multiDestinationMode) {
        if (multiDestinationMode) {
            isMultiDestination = true
        }
    }
    if (isLocationRetrieved) {
        Box {
            AndroidView(
                factory = {
                    MapView(context).apply {
                        mapView = this
                        moveCamera(LatLng(latitude, longitude), 0f)
                        setZoom(16f, 0f)
                        isTrafficEnabled = true
                        isPoiEnabled = true
                    }
                },
                update = {
                    mapView?.let { map ->
                        val currentLatLng = LatLng(latitude, longitude)
                        val threshold = 5.0 // meters
                        val distance = FloatArray(1)
                        Location.distanceBetween(
                            previousLatitude,
                            previousLongitude,
                            currentLatLng.latitude,
                            currentLatLng.longitude, distance
                        )
                        if (distance[0] >= threshold) {
                            outerCircle?.let { map.removeCircle(it) }
                            outerCircle = Circle(
                                currentLatLng,
                                80.0,
                                Color(2, 119, 189, 30),
                                getCircleLineStyle()
                            )
                            map.addCircle(outerCircle)

                            innerCircle?.let { map.removeCircle(it) }
                            innerCircle = Circle(
                                currentLatLng,
                                20.0,
                                Color(2, 119, 189, 190),
                                getCircleLineStyle()
                            )
                            map.addCircle(innerCircle)

                            previousLatitude = latitude
                            previousLongitude = longitude
                        }

                        if (routePolyline.isNotEmpty()) {
                            val newDestination = routePolyline.last()
                            if (previousDestination == null || previousDestination != newDestination) {
                                polyline?.let { map.removePolyline(it) }

                                val lineStyleBuilder = LineStyleBuilder()
                                lineStyleBuilder.color = Color(124, 185, 255, 255)
                                lineStyleBuilder.width = 5f

                                polyline =
                                    Polyline(
                                        routePolyline as ArrayList,
                                        lineStyleBuilder.buildStyle()
                                    )
                                map.addPolyline(polyline)

                                previousDestination = newDestination
                            }
                        }

                        mapView?.setOnMapLongClickListener { latLng ->
                            onMapLongClick(latLng)
                            markers =
                                handleMarkers(mapView, markers, latLng, isMultiDestination, context)
                        }
                    }
                }
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 12.dp, end = 12.dp),
                horizontalAlignment = Alignment.End

            ) {
                mapView?.let {
                    CurrentLocationButton(
                        map = it,
                        currentLocation = LatLng(latitude, longitude)
                    )
                }
                FloatingCustomButton(
                    imageVector = Icons.Filled.Search,
                    text = "کجا میخوای بری؟",
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 12.dp)
                        .fillMaxWidth(),
                    onClick = { onSearchClick() }
                )
            }
        }
    } else {
        CircularIndeterminateProgressBar(isDisplayed = true)
    }
}