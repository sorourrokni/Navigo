package com.example.navigo.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigo.data.model.common.Search
import com.example.navigo.data.repository.SearchRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: SearchRepository,
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> get() = _searchResults

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    private val _latitude = MutableStateFlow<Double?>(null)
    private val _longitude = MutableStateFlow<Double?>(null)

    init {
        viewModelScope.launch {
            combine(_query, _latitude, _longitude) { query, lat, lon ->
                Triple(query, lat, lon)
            }
                .debounce(300)
                .collect { (query, lat, lon) ->
                    if (query.isEmpty()) {
                        _searchResults.value = emptyList()
                    } else if (lat != null && lon != null) {
                        performSearch(query, lat, lon)
                    }
                }
        }
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun updateLocation(lat: Double, lon: Double) {
        _latitude.value = lat
        _longitude.value = lon
    }

    private fun performSearch(term: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            repository.search(term, latitude, longitude) { response ->
                val searchItems = response?.items ?: emptyList()
                val resultsWithDistance = searchItems.map { item ->
                    val distance = calculateDistance(
                        latitude,
                        longitude,
                        item.location.latitude,
                        item.location.longitude
                    )
                    SearchResult(item, distance)
                }
                val sortedResults = resultsWithDistance.sortedBy { it.distance }
                _searchResults.value = sortedResults
            }
        }
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lon1, lat2, lon2, results)
        return results[0]
    }
}

data class SearchResult(val search: Search, val distance: Float)

class SearchViewModelFactory(
    private val searchRepository: SearchRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(searchRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
