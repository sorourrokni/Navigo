package com.example.navigo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigo.data.model.common.Point
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

    fun fetchRoute(origin: String, destination: String) {
        viewModelScope.launch {
            directionRepository.getRouteWithTraffic(origin, destination) { directionResponse ->
                directionResponse?.let {
                    val polylinePoints =
                        decodePolyline(directionResponse.routes[0].overviewPolyline.points)
                    _routePolyline.value = polylinePoints
                }
            }
        }
    }

    fun updateRoute(points: List<Point>) {
        val waypoints = points.joinToString("|") { "${it.location[0]},${it.location[1]}" }

        viewModelScope.launch {
            directionRepository.getOptimalRoute(
                waypoints,
                onSuccess = { response ->
                    _routePoints.value = response.points
                },
                onFailure = { throwable ->
                    _error.value = throwable.message
                }
            )
        }
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
