package com.amro.venuefinder.repository

import com.amro.venuefinder.data.VenueDetailResult
import com.amro.venuefinder.data.VenueSearchResult
import com.amro.venuefinder.network.RetrofitClient

class VenueRemoteDataSource : VenueDataSource {

    override suspend fun search(
        near: String,
        limit: Int,
        radius: Int,
        clientId: String,
        clientSecret: String,
        version: Int
    ): VenueSearchResult? {
        return RetrofitClient.buildService(VenueDataSource::class.java).search(near)
    }

    override suspend fun details(
        id: String,
        clientId: String,
        clientSecret: String,
        version: Int
    ): VenueDetailResult? {
        return RetrofitClient.buildService(VenueDataSource::class.java).details(id = id)
    }

    companion object {

        @Volatile
        private var INSTANCE: VenueRemoteDataSource? = null

        fun getInstance(): VenueRemoteDataSource {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = VenueRemoteDataSource()
                INSTANCE = instance
                return instance
            }
        }
    }
}