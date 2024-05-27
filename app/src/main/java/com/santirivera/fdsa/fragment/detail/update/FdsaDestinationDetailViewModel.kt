package com.santirivera.fdsa.fragment.detail.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.usecase.destination.delete.DeleteDestinationUseCase
import com.santirivera.domain.usecase.destination.update.UpdateDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FdsaDestinationDetailViewModel @Inject constructor(
    private val deleteDestinationUseCase: DeleteDestinationUseCase,
    private val updateDestinationUseCase: UpdateDestinationUseCase
) : ViewModel() {

    private val _deleteDestinationResult = MutableLiveData<Boolean>()
    val deleteDestinationResult: LiveData<Boolean> = _deleteDestinationResult

    private val _updateDestinationResult = MutableLiveData<Boolean>()
    val updateDestinationResult: LiveData<Boolean> = _updateDestinationResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun deleteDestination(destination: DestinationItem) {
        viewModelScope.launch {
            try {
                deleteDestinationUseCase.execute(destination.id)
                _deleteDestinationResult.value = true
            } catch (e: Exception) {
                _error.value = "Failed to delete destination: ${e.message}"
            }
        }
    }

    fun updateDestination(destination: DestinationItem) {
        viewModelScope.launch {
            try {
                updateDestinationUseCase.execute(destination)
                _updateDestinationResult.value = true
            } catch (e: Exception) {
                _error.value = "Failed to update destination: ${e.message}"
            }
        }
    }
}
