package com.santirivera.fdsa.fragment.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santirivera.domain.model.DestinationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import com.santirivera.domain.usecase.destination.list.GetAllDestinationsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FdsaDestinationListViewModel @Inject constructor(private val getAllDestinationsUseCase: GetAllDestinationsUseCase) :
    ViewModel() {
    private val _destinations = MutableLiveData<List<DestinationItem>>()
    val destinations: LiveData<List<DestinationItem>> get() = _destinations

    private val _filteredDestinations = MutableLiveData<List<DestinationItem>>()
    val filteredDestinations: LiveData<List<DestinationItem>> get() = _filteredDestinations

    fun loadDestinations() {
        viewModelScope.launch {
            try {
                val result = getAllDestinationsUseCase.execute(null)
                _destinations.postValue(result)
                _filteredDestinations.postValue(result)
            } catch (e: Exception) {
                // Handle error case
            }
        }
    }

    fun filter(query: String) {
        val filteredList = _destinations.value?.filter {
            it.name.contains(query, true)
        } ?: emptyList()
        _filteredDestinations.postValue(filteredList)
    }
}