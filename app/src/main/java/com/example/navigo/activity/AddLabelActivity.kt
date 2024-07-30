package com.example.navigo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.carto.graphics.Color
import com.carto.styles.TextMargins
import com.carto.styles.TextStyleBuilder
import org.neshan.common.model.LatLng
import org.neshan.mapsdk.MapView
import org.neshan.mapsdk.model.Label

class AddLabelActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
        }
    }
}

fun addLabel(mapView: MapView, loc: LatLng, existingLabel: Label?) {
    if (existingLabel != null) {
        mapView.removeLabel(existingLabel)
    }

    val textStyleBuilder = TextStyleBuilder().apply {
        fontSize = 25f
        color = Color(-0x1)
        strokeWidth = 0.5f
        strokeColor = Color(-0x1)
        textMargins = TextMargins(5, 2, 5, 2)
        backgroundColor = Color(-0x10000)
    }
    val textStyle = textStyleBuilder.buildStyle()

    val newLabel = Label(loc, textStyle, "مکان انتخاب شده")
    mapView.addLabel(newLabel)
}
