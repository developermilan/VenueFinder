package com.amro.venuefinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amro.venuefinder.repository.VenueRepository
import com.amro.venuefinder.ui.detail.VenueDetailViewModel
import com.amro.venuefinder.ui.list.VenueListViewModel
import java.lang.IllegalArgumentException

class ViewModelProviderFactory(private val repository: VenueRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenueListViewModel::class.java)) {
            return VenueListViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(VenueDetailViewModel::class.java)) {
            return VenueDetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}