package com.amro.venuefinder.repository

import com.amro.venuefinder.data.Venue
import com.amro.venuefinder.data.VenueDetailResult
import com.amro.venuefinder.data.VenueSearchResult

class VenueRepository(
    private val isInternetConnected: Boolean,
    private val remoteDataSource: VenueRemoteDataSource,
    private val localDataSource: VenueLocalDataSource
) :
    VenueDataSource {

    override suspend fun search(
        near: String,
        limit: Int,
        radius: Int,
        clientId: String,
        clientSecret: String,
        version: Int
    ): VenueSearchResult? {
        return if (isInternetConnected) {
            remoteDataSource.search(near)
        } else {
            localDataSource.search(near)
        }
    }

    override suspend fun details(
        id: String,
        clientId: String,
        clientSecret: String,
        version: Int
    ): VenueDetailResult? {
        return if (isInternetConnected) {
            remoteDataSource.details(id)
        } else {
            localDataSource.details(id)
        }
    }

    suspend fun insertVenues(venues: List<Venue>) {
        if (isInternetConnected) {
            localDataSource.insert(venues)
        }
    }

    suspend fun deleteAll() {
        if (isInternetConnected) {
            localDataSource.deleteAll()
        }
    }
}