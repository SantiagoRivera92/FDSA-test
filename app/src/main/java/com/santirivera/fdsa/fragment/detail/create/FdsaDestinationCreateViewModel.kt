package com.santirivera.fdsa.fragment.detail.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santirivera.domain.model.DestinationItem
import com.santirivera.domain.usecase.destination.create.CreateDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FdsaDestinationCreateViewModel @Inject constructor(
    private val createDestinationUseCase: CreateDestinationUseCase
) : ViewModel() {

    private val _creationResult = MutableLiveData<Boolean>()
    val creationResult: LiveData<Boolean> = _creationResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun createDestination(destination: DestinationItem) {
        viewModelScope.launch {
            try {
                createDestinationUseCase.execute(destination)
                _creationResult.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "An error occurred"
            }
        }
    }
}