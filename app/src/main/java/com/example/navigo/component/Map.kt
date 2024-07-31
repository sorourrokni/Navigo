package com.example.navigo.component

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.carto.graphics.Color
import com.carto.styles.AnimationStyleBuilder
import com.carto.styles.AnimationType
import com.carto.styles.LineStyle
import com.carto.styles.LineStyleBuilder
import com.carto.styles.MarkerStyleBuilder
import com.example.navigo.viewModel.LocationViewModel
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.R
import org.neshan.mapsdk.internal.utils.BitmapUtils
import org.neshan.mapsdk.model.Circle
import org.neshan.mapsdk.model.Marker
import org.neshan.mapsdk.model.Polyline
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt


@Composable
fun Map(locationViewModel: LocationViewModel) {
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

    LaunchedEffect(Unit) {
        locationViewModel.requestLocationUpdates()
    }

    if (isLocationRetrieved) {
        AndroidView(
            factory = {
                MapView(context).apply {
                    mapView = this
                    moveCamera(LatLng(latitude, longitude), 0f)
                    setZoom(14f, 0f)
                    isTrafficEnabled = true
                    isPoiEnabled = true

                    setOnMapLongClickListener { latLng ->
                        markers.forEach { mapView!!.removeMarker(it) }
                        val newMarker = createMarker(context = context, loc = latLng)
                        mapView!!.addMarker(newMarker)
                        markers = markers + newMarker
                    }
                }
            },
            update = {
                mapView?.let { map ->
                    val currentLatLng = LatLng(latitude, longitude)
                    val threshold = 5.0 // meters
                    val distance =
                        distanceInMeters(previousLatitude, previousLongitude, latitude, longitude)

                    if (distance >= threshold) {
                        // Remove and recreate outer circle
                        outerCircle?.let { map.removeCircle(it) }
                        outerCircle = Circle(
                            currentLatLng,
                            30.0,
                            Color(2, 119, 189, 60),
                            getCircleLineStyle()
                        )
                        map.addCircle(outerCircle)

                        // Remove and recreate inner circle
                        innerCircle?.let { map.removeCircle(it) }
                        innerCircle = Circle(
                            currentLatLng,
                            10.0,
                            Color(2, 119, 189, 190),
                            getCircleLineStyle()
                        )
                        map.addCircle(innerCircle)

                        // Update camera position when location changes
                        map.moveCamera(currentLatLng, 0f)

                        // Update previous location to the current location
                        previousLatitude = latitude
                        previousLongitude = longitude
                    }

                    map.setOnMapLongClickListener { latLng ->
                        markers.forEach { map.removeMarker(it) }
                        val newMarker = createMarker(context = context, loc = latLng)
                        map.addMarker(newMarker)
                        markers = markers + newMarker
                    }
                }
            }
        )
    } else {
        Text(text = "Retrieving location...", modifier = Modifier.padding(16.dp))
    }
}

private fun getCircleLineStyle(): LineStyle {
    val lineStyleBuilder = LineStyleBuilder()
    lineStyleBuilder.color = Color(0, 0, 0, 0)
    lineStyleBuilder.width = 1f
    return lineStyleBuilder.buildStyle()
}

private fun createMarker(loc: LatLng, context: Context): Marker {
    val animStBl = AnimationStyleBuilder()
    animStBl.fadeAnimationType = AnimationType.ANIMATION_TYPE_SMOOTHSTEP
    animStBl.sizeAnimationType = AnimationType.ANIMATION_TYPE_SPRING
    animStBl.phaseInDuration = 0.5f
    animStBl.phaseOutDuration = 0.5f
    val animSt = animStBl.buildStyle()

    val markStCr = MarkerStyleBuilder()
    markStCr.animationStyle = animSt
    markStCr.size = 30f
    markStCr.bitmap = BitmapUtils.createBitmapFromAndroidBitmap(
        BitmapFactory.decodeResource(
            context.resources, R.drawable.ic_cluster_marker_blue
        )
    )
    val markSt = markStCr.buildStyle()
    return Marker(loc, markSt)
}

private fun distanceInMeters(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
    val earthRadius = 6371000.0 // Radius of the Earth in meters

    val lat1Rad = Math.toRadians(lat1)
    val lon1Rad = Math.toRadians(lon1)
    val lat2Rad = Math.toRadians(lat2)
    val lon2Rad = Math.toRadians(lon2)

    val deltaLat = lat2Rad - lat1Rad
    val deltaLon = lon2Rad - lon1Rad

    val a = sin(deltaLat / 2).pow(2) +
            cos(lat1Rad) * cos(lat2Rad) * sin(deltaLon / 2).pow(2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))

    return earthRadius * c
}

fun drawLine(mapView: MapView, latLng: ArrayList<LatLng>): Polyline {
    val polyline = Polyline(latLng, getDirectionLineStyle())
    mapView.addPolyline(polyline)
    mapView.moveCamera(LatLng(35.769368, 51.327650), 0.25f)
    mapView.setZoom(14f, 0f)
    return polyline
}

fun getDirectionLineStyle(): LineStyle {
    val lineStyleBuilder = LineStyleBuilder()
    lineStyleBuilder.color = Color(2, 119, 189, 190)
    lineStyleBuilder.width = 4f
    return lineStyleBuilder.buildStyle()
}