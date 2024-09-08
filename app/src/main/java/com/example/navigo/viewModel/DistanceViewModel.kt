package com.example.navigo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigo.data.repository.DistanceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.neshan.common.model.LatLng

class DistanceViewModel(private val distanceRepository: DistanceRepository) : ViewModel() {
    private val _carDistanceWithTraffic = MutableStateFlow<Pair<String, String>?>(null)
    val carDistanceWithTraffic: StateFlow<Pair<String, String>?> = _carDistanceWithTraffic

    private val _motorcycleDistanceWithTraffic = MutableStateFlow<Pair<String, String>?>(null)
    val motorcycleDistanceWithTraffic: StateFlow<Pair<String, String>?> =
        _motorcycleDistanceWithTraffic

    private val _carDistanceWithoutTraffic = MutableStateFlow<Pair<String, String>?>(null)
    val carDistanceWithoutTraffic: StateFlow<Pair<String, String>?> = _carDistanceWithoutTraffic

    private val _motorcycleDistanceWithoutTraffic = MutableStateFlow<Pair<String, String>?>(null)
    val motorcycleDistanceWithoutTraffic: StateFlow<Pair<String, String>?> =
        _motorcycleDistanceWithoutTraffic

    fun fetchCarDistanceWithTraffic(
        userLatLng: LatLng,
        destinationLatLng: LatLng,
        onResult: ((String, String) -> Unit)? = null
    ) {
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
                _carDistanceWithTraffic.value = Pair(distance, duration)
                if (onResult != null) {
                    onResult(distance, duration)
                }
            } else {
                _carDistanceWithTraffic.value = Pair("Error", "Error")
                if (onResult != null) {
                    onResult("Error", "Error")
                }
            }
        }
    }

    fun fetchCarDistanceWithoutTraffic(
        userLatLng: LatLng,
        destinationLatLng: LatLng,
        onResult: ((String, String) -> Unit)? = null
    ) {
        val origins = "${userLatLng.latitude},${userLatLng.longitude}"
        val destinations = "${destinationLatLng.latitude},${destinationLatLng.longitude}"

        distanceRepository.getDistanceMatrixWithoutTraffic(
            type = "car",
            origins = origins,
            destinations = destinations
        ) { distanceMatrixResponse ->
            if (distanceMatrixResponse != null) {
                val distance = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.distance?.text ?: "Unknown"
                val duration = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.duration?.text ?: "Unknown"

                _carDistanceWithoutTraffic.value = Pair(distance, duration)
                if (onResult != null) {
                    onResult(distance, duration)
                }
            } else {
                _carDistanceWithoutTraffic.value = Pair("Error", "Error")
                if (onResult != null) {
                    onResult("Error", "Error")
                }
            }
        }
    }

    fun fetchMotorcycleDistanceWithTraffic(userLatLng: LatLng, destinationLatLng: LatLng) {
        val origins = "${userLatLng.latitude},${userLatLng.longitude}"
        val destinations = "${destinationLatLng.latitude},${destinationLatLng.longitude}"

        distanceRepository.getDistanceMatrixWithTraffic(
            type = "motorcycle",
            origins = origins,
            destinations = destinations
        ) { distanceMatrixResponse ->
            if (distanceMatrixResponse != null) {
                val distance = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.distance?.text ?: "Unknown"
                val duration = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.duration?.text ?: "Unknown"

                _motorcycleDistanceWithTraffic.value = Pair(distance, duration)
            } else {
                _motorcycleDistanceWithTraffic.value = Pair("Error", "Error")
            }
        }
    }

    fun fetchMotorcycleDistanceWithoutTraffic(userLatLng: LatLng, destinationLatLng: LatLng) {
        val origins = "${userLatLng.latitude},${userLatLng.longitude}"
        val destinations = "${destinationLatLng.latitude},${destinationLatLng.longitude}"

        distanceRepository.getDistanceMatrixWithoutTraffic(
            type = "motorcycle",
            origins = origins,
            destinations = destinations
        ) { distanceMatrixResponse ->
            if (distanceMatrixResponse != null) {
                val distance = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.distance?.text ?: "Unknown"
                val duration = distanceMatrixResponse.rows.firstOrNull()
                    ?.elements?.firstOrNull()?.duration?.text ?: "Unknown"

                _motorcycleDistanceWithoutTraffic.value = Pair(distance, duration)
            } else {
                _motorcycleDistanceWithoutTraffic.value = Pair("Error", "Error")
            }
        }
    }
}

class DistanceViewModelFactory(
    private val distanceRepository: DistanceRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DistanceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DistanceViewModel(distanceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}