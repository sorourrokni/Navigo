package com.example.navigo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigo.data.model.common.Address
import com.example.navigo.data.repository.AddressRepository
import com.example.navigo.data.repository.DistanceRepository
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng

class CoordinateViewModel(
    private val addressRepository: AddressRepository,
    private val distanceRepository: DistanceRepository
) : ViewModel() {

    private val _addressDetails = mutableStateOf<Address?>(null)
    val addressDetails: State<Address?> get() = _addressDetails

    private val _distanceDetails = mutableStateOf<Pair<String, String>?>(null)
    val distanceDetails: State<Pair<String, String>?> = _distanceDetails

    fun fetchAddressDetails(lat: Double, lng: Double) {
        viewModelScope.launch {
            addressRepository.getAddress(lat, lng) { address ->
                _addressDetails.value = address
            }
        }
    }

    fun fetchDistanceAndTime(userLatLng: LatLng, destinationLatLng: LatLng) {
        val origins = "${userLatLng.latitude},${userLatLng.longitude}"
        val destinations = "${destinationLatLng.latitude},${destinationLatLng.longitude}"

        distanceRepository.getDistanceMatrixWithTraffic(
            type = "car",
            origins = origins,
            destinations = destinations
        ) { distanceMatrixResponse ->
            if (distanceMatrixResponse != null) {
                val distance = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.distance?.text ?: "Unknown"
                val duration = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.duration?.text ?: "Unknown"

                _distanceDetails.value = Pair(distance, duration)
            } else {
                _distanceDetails.value = Pair("Error", "Error")
            }
        }
    }
}

class CoordinateViewModelFactory(
    private val locationRepository: AddressRepository,
    private val distanceRepository: DistanceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CoordinateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CoordinateViewModel(locationRepository, distanceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
