package com.amro.venuefinder.repository

import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.data.VenueDetailResult
import com.amro.venuefinder.data.VenueSearchResult

class VenueRepository(
    private val remoteDataSource: VenueRemoteDataSource,
    private val localDataSource: VenueLocalDataSource
) {

    suspend fun search(
        near: String,
        isInternetConnected: Boolean
    ): VenueSearchResult? {
        return if (isInternetConnected) {
            remoteDataSource.search(near)
        } else {
            localDataSource.search(near)
        }
    }

    suspend fun details(
        id: String,
        isInternetConnected: Boolean
    ): VenueDetailResult? {
        return if (isInternetConnected) {
            remoteDataSource.details(id)
        } else {
            localDataSource.details(id)
        }
    }

    suspend fun insertVenues(venues: List<Venue>, isInternetConnected: Boolean) {
        if (isInternetConnected) {
            localDataSource.insert(venues)
        }
    }

    suspend fun deleteAll(isInternetConnected: Boolean) {
        if (isInternetConnected) {
            localDataSource.deleteAll()
        }
    }
}