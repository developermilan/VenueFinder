package com.amro.venuefinder.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.repository.VenueRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class VenueListViewModel(private val repository: VenueRepository) : ViewModel() {

    private lateinit var venueList: ArrayList<Venue>
    val venues = MutableLiveData<List<Venue>>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Throwable>()
    val clickedItemPosition = MutableLiveData<Int>()
    private var lastQuery = ""

    fun initialise() {
        search(lastQuery)
    }

    fun search(query: String?) {
        venueList = ArrayList()
        lastQuery = query ?: ""

        if (lastQuery != "") {
            loadVenues()
        }
    }

    private fun loadVenues() {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            isLoading.postValue(false)
            isError.postValue(throwable)
        }

        viewModelScope.launch(exceptionHandler) {
            isLoading.postValue(true)

            val result = repository.search(lastQuery)
            venues.postValue(result?.searchResponse?.venueList)

            repository.deleteAll()
            repository.insertVenues(result?.searchResponse?.venueList ?: emptyList())

            isLoading.postValue(false)
        }
    }

    fun itemClicked(position: Int) {
        clickedItemPosition.postValue(position)
    }

    fun shouldListVisible(): Boolean {
        return isLoading.value != true && isError.value == null
    }
}