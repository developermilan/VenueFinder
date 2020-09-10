package com.amro.venuefinder.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.repository.VenueRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class VenueDetailViewModel(private val repository: VenueRepository) : ViewModel() {

    val isError = MutableLiveData<Throwable>()
    val venue = MutableLiveData<Venue>()

    fun fetchVenueDetails(vid: String, isInternetConnected: Boolean) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            isError.postValue(throwable)
        }

        viewModelScope.launch(exceptionHandler) {

            val result = repository.details(vid, isInternetConnected = isInternetConnected)
            venue.postValue(result?.response?.venue)
        }
    }

}