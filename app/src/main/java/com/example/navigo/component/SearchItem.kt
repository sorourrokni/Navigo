package com.example.navigo.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import com.example.navigo.data.model.common.Search
import org.neshan.common.model.LatLng
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SearchItem(
    search: Search,
    onClick: (LatLng) -> Unit,
    distance: Float
) {
    ElevatedCard(
        onClick = { onClick(LatLng(search.location.latitude, search.location.longitude)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier.padding(end = 24.dp, start = 24.dp),
                text = formatDistance(distance = distance),
                style = MaterialTheme.typography.titleLarge.copy(
                    textDirection = TextDirection.Rtl,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.surface
                )
            )
            Column(
                modifier = Modifier.padding(
                    start = 12.dp,
                    end = 12.dp,
                    bottom = 12.dp,
                    top = 12.dp
                ).fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    text = search.title ?: "عنوان نامشخص",
                    style = MaterialTheme.typography.titleLarge.copy(
                        textDirection = TextDirection.Rtl,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                Text(
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    text = search.address ?: "آدرس نامشخص",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.surfaceTint,
                        textDirection = TextDirection.Rtl,
                        textAlign = TextAlign.Start
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = search.neighbourhood ?: "محله نامشخص",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                            textDirection = TextDirection.Rtl,
                            textAlign = TextAlign.Start
                        )
                    )
                    VerticalDivider(
                        color = MaterialTheme.colorScheme.surfaceTint,
                        thickness = 1.dp,
                        modifier = Modifier
                            .height(12.dp)
                            .padding(start = 4.dp, end = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun formatDistance(distance: Float): String {
    val numberFormat = NumberFormat.getInstance(Locale("fa", "IR"))

    return if (distance < 1000) {
        "${numberFormat.format(distance.toInt())} متر"
    } else {
        val distanceInKm = distance / 1000
        String.format(
            Locale("fa", "IR"),
            "%.1f کیلومتر",
            distanceInKm
        ).replace(',', '٫')
    }
}