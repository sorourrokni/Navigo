package com.example.navigo.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigo.data.model.common.Point
import com.example.navigo.data.model.common.Step
import com.example.navigo.data.repository.DirectionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.neshan.common.model.LatLng

class DirectionViewModel(private val directionRepository: DirectionRepository) : ViewModel() {
    private val _routePolyline = MutableStateFlow<List<LatLng>>(emptyList())
    val routePolyline: StateFlow<List<LatLng>> = _routePolyline

    private val _routePoints = MutableStateFlow<List<Point>>(emptyList())
    val routePoints: StateFlow<List<Point>> get() = _routePoints

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val _navigationInstructions = MutableStateFlow<List<String?>>(emptyList())
    val navigationInstructions: StateFlow<List<String?>> get() = _navigationInstructions

    private val _steps = MutableStateFlow<List<Step>>(emptyList())
    val steps: StateFlow<List<Step>> get() = _steps

    private val _destinations = MutableStateFlow<List<Point>>(emptyList())
    val destinations: StateFlow<List<Point>> get() = _destinations

    companion object {
        const val THRESHOLD_DISTANCE = 50 // Threshold distance to detect approaching the next step
    }

    fun clearRoute() {
        _routePolyline.value = emptyList()
    }

    fun fetchRouteWithTraffic(origin: String, destination: String) {
        viewModelScope.launch {
            directionRepository.getRouteWithTraffic(origin, destination) { directionResponse ->
                directionResponse?.let {
                    val route = directionResponse.routes.firstOrNull()
                    route?.let {
                        _steps.value = route.legs.firstOrNull()?.steps ?: emptyList()
                        _routePolyline.value = decodePolyline(route.overviewPolyline.points)
                        processInstructions(_steps.value)
                    }
                }
            }
        }
    }

    private fun processInstructions(steps: List<Step>) {
        val instructions = steps.map { step ->
            step.instruction
        }
        _navigationInstructions.value = instructions
    }

    fun updateNavigation(currentLocation: LatLng) {
        _steps.value.forEach { step ->
            val stepLocation = LatLng(step.startLocation[0], step.startLocation[1])
            val distanceToStep = calculateDistance(currentLocation, stepLocation)

            if (distanceToStep < THRESHOLD_DISTANCE) {
                val updatedInstructions = _navigationInstructions.value.drop(1)
                _navigationInstructions.value = updatedInstructions
                showInstruction(updatedInstructions.firstOrNull())
            }
        }
    }

    fun showInstruction(instruction: String?) {
        instruction?.let {
            println("دستورالعمل بعدی: $instruction")
        }
    }

    private fun calculateDistance(currentLocation: LatLng, stepLocation: LatLng): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            currentLocation.latitude, currentLocation.longitude,
            stepLocation.latitude, stepLocation.longitude, results
        )
        return results[0]
    }

    fun updateRoute() {
        val waypoints =
            _destinations.value.joinToString("|") { "${it.location[0]},${it.location[1]}" }

        viewModelScope.launch {
            directionRepository.getOptimalRoute(
                waypoints,
                onSuccess = { response ->
                    _routePoints.value = response.points
                    _routePolyline.value =
                        response.points.map { LatLng(it.location[0], it.location[1]) }
                },
                onFailure = { throwable ->
                    _error.value = throwable.message
                }
            )
        }
    }

    fun addDestination(point: Point) {
        _destinations.value += point
        updateRoute()
    }


    private fun decodePolyline(encoded: String): List<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0

        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }
}

class DirectionViewModelFactory(
    private val directionRepository: DirectionRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DirectionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DirectionViewModel(directionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
