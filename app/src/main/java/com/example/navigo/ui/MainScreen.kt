package com.example.navigo.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.navigo.component.DynamicTopSheet
import com.example.navigo.component.LocationContent
import com.example.navigo.component.Map
import com.example.navigo.data.repository.AddressRepository
import com.example.navigo.data.repository.DirectionRepository
import com.example.navigo.data.repository.DistanceRepository
import com.example.navigo.navigation.NavigationItem
import com.example.navigo.viewModel.AddressViewModel
import com.example.navigo.viewModel.AddressViewModelFactory
import com.example.navigo.viewModel.DirectionViewModel
import com.example.navigo.viewModel.DirectionViewModelFactory
import com.example.navigo.viewModel.DistanceViewModel
import com.example.navigo.viewModel.DistanceViewModelFactory
import com.example.navigo.viewModel.LocationViewModel
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    locationViewModel: LocationViewModel,
    navController: NavHostController,
    initialLatLng: LatLng? = null
) {
    val addressViewModel: AddressViewModel = viewModel(
        factory = AddressViewModelFactory(
            AddressRepository()
        )
    )
    val directionViewModel: DirectionViewModel = viewModel(
        factory = DirectionViewModelFactory(
            DirectionRepository()
        )
    )
    val distanceViewModel: DistanceViewModel = viewModel(
        factory = DistanceViewModelFactory(
            DistanceRepository()
        )
    )

    val latitude by locationViewModel.latitude.collectAsState()
    val longitude by locationViewModel.longitude.collectAsState()
    val location = LatLng(latitude, longitude)

    var isMultiDestination by remember { mutableStateOf(false) }
    val addressDetails by addressViewModel.addressDetails
    val carDistanceDetails by distanceViewModel.carDistanceWithTraffic.collectAsState()
    val motorcycleDistanceDetails by distanceViewModel.motorcycleDistanceWithTraffic.collectAsState()
    val routePolyline by directionViewModel.routePolyline.collectAsState()
    val tspRoute by directionViewModel.routePoints.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var userLatLng by remember { mutableStateOf<LatLng?>(null) }
    var clickedLatLng by remember { mutableStateOf<LatLng?>(null) }
    var showTopSheet by remember { mutableStateOf(false) }
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    DisposableEffect(backDispatcher) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (sheetState.isVisible) {
                    scope.launch {
                        sheetState.hide()
                    }
                } else if (showTopSheet) {
                    showTopSheet = false
                } else if (clickedLatLng != null) {
                    clickedLatLng = null
                } else if (routePolyline.isNotEmpty()) {
                    directionViewModel.clearRoute()
                } else {
                    navController.popBackStack()
                }
            }
        }
        backDispatcher?.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }
    LaunchedEffect(initialLatLng) {
        initialLatLng?.let {
            addressViewModel.fetchAddressDetails(it.latitude, it.longitude)
            userLatLng = LatLng(locationViewModel.latitude.value, locationViewModel.longitude.value)
            userLatLng?.let { userLocation ->
                distanceViewModel.fetchCarDistanceWithTraffic(userLocation, it)
                distanceViewModel.fetchMotorcycleDistanceWithTraffic(userLocation, it)
            }
            clickedLatLng = initialLatLng
            scope.launch {
                if (!sheetState.isVisible) {
                    sheetState.show()
                }
            }
        }
    }

    Box {
        Map(
            locationViewModel = locationViewModel,
            searchSelectedLocation = initialLatLng,
            routePolyline = routePolyline,
            onSearchClick = {
                navController.navigate(NavigationItem.Search.route)
            },
            onMapLongClick = { latLng ->
                clickedLatLng = latLng
                userLatLng =
                    LatLng(locationViewModel.latitude.value, locationViewModel.longitude.value)
                addressViewModel.fetchAddressDetails(latLng.latitude, latLng.longitude)
                userLatLng?.let { userLocation ->
                    distanceViewModel.fetchCarDistanceWithTraffic(userLocation, latLng)
                    distanceViewModel.fetchMotorcycleDistanceWithTraffic(userLocation, latLng)
                }
                showTopSheet = true
                scope.launch {
                    sheetState.apply {
                        if (!isVisible) show() else hide()
                    }
                }
            },
        )
        if (sheetState.isVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    scope.launch {
                        sheetState.apply {
                            hide()
                        }
                    }
                }, modifier = Modifier.height(320.dp),
                dragHandle = null
            ) {
                LocationContent(
                    address = addressDetails,
                    distanceDetails = carDistanceDetails,
                    onRouteButtonClick = {
                        userLatLng?.let { origin ->
                            clickedLatLng?.let { destination ->
                                directionViewModel.fetchRouteWithTraffic(
                                    origin = "${origin.latitude},${origin.longitude}",
                                    destination = "${destination.latitude},${destination.longitude}"
                                )
                            }
                        }
                        showTopSheet = true
                        scope.launch {
                            sheetState.apply {
                                hide()
                            }
                        }
                    })
            }
        }

        AnimatedVisibility(
            visible = showTopSheet,
            enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            DynamicTopSheet(
                onDismiss = { showTopSheet = true },
                address = addressDetails,
                carDistanceDetails = carDistanceDetails,
                motorcycleDistanceDetails = motorcycleDistanceDetails,
                onMultiDestinationRouting = {}
            )
        }
    }
}
