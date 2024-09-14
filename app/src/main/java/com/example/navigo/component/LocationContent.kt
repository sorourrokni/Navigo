package com.example.navigo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.navigo.R
import com.example.navigo.data.model.common.Address

@Composable
fun LocationContent(
    address: Address?,
    distanceDetails: Pair<String, String>?,
    onRouteButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (address != null) {
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 12.dp),
            ) {
                Column {
                    address.routeName?.let {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            text = it, style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                    address.neighbourhood?.let {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 12.dp),
                            text = it,
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                        )
                    }
                }
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp),
                    painter = painterResource(id = R.drawable.ic_routing),
                    contentDescription = "location icon"
                )
            }
            Divider(modifier = Modifier.padding(start = 24.dp, end = 24.dp))
            Row(
                modifier = Modifier
                    .padding(top = 12.dp, end = 24.dp)
                    .align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                address.address?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                }
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp),
                    painter = painterResource(id = R.drawable.ic_marker_bold),
                    contentDescription = "location icon"
                )
            }
            distanceDetails?.let {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .padding(top = 12.dp, end = 24.dp)
                        .align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(end = 8.dp), text = it.first,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(16.dp)
                            .width(1.dp)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp), text = it.second,
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp),
                        painter = painterResource(id = R.drawable.ic_car_bold),
                        contentDescription = "car icon"
                    )
                }


            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(all = 24.dp)
                    .fillMaxWidth(),
                onClick = onRouteButtonClick,
            ) {
                Icon(
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier.padding(end = 8.dp),
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = "car icon"
                )
                Text(
                    "مسیر ها",
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.background)
                )
            }
        } else {
            CircularIndeterminateProgressBar(isDisplayed = true)
        }
    }
}
