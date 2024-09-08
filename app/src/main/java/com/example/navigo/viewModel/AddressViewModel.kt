package com.example.navigo.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.navigo.data.model.common.Address
import com.example.navigo.data.repository.AddressRepository
import kotlinx.coroutines.launch

class AddressViewModel(
    private val addressRepository: AddressRepository
) : ViewModel() {

    private val _addressDetails = mutableStateOf<Address?>(null)
    val addressDetails: State<Address?> get() = _addressDetails

    fun fetchAddressDetails(lat: Double, lng: Double) {
        viewModelScope.launch {
            addressRepository.getAddress(lat, lng) { address ->
                _addressDetails.value = address
            }
        }
    }
}

class AddressViewModelFactory(
    private val addressRepository: AddressRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddressViewModel(addressRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
