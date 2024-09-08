package com.example.navigo.component

import android.content.Context
import android.graphics.BitmapFactory
import com.carto.graphics.Color
import com.carto.styles.AnimationStyleBuilder
import com.carto.styles.AnimationType
import com.carto.styles.LineStyle
import com.carto.styles.LineStyleBuilder
import com.carto.styles.MarkerStyleBuilder
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.R
import org.neshan.mapsdk.internal.utils.BitmapUtils
import org.neshan.mapsdk.model.Marker

fun handleMarkers(
    mapView: MapView?,
    markers: List<Marker>,
    latLng: LatLng,
    isMultiDestination: Boolean,
    context: Context
): List<Marker> {
    mapView?.let { map ->
        if (isMultiDestination) {
            val newMarker = createMarker(latLng, context)
            map.addMarker(newMarker)
            return markers + newMarker
        } else {
            markers.forEach { map.removeMarker(it) }
            val newMarker = createMarker(latLng, context)
            map.addMarker(newMarker)
            map.moveCamera(LatLng(latLng.latitude, latLng.longitude), 0f)
            map.setZoom(16f, 0f)
            return listOf(newMarker)
        }
    }
    return markers
}

fun createMarker(loc: LatLng, context: Context): Marker {
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

fun getCircleLineStyle(): LineStyle {
    val lineStyleBuilder = LineStyleBuilder()
    lineStyleBuilder.color = Color(0, 0, 0, 0)
    lineStyleBuilder.width = 1f
    return lineStyleBuilder.buildStyle()
}