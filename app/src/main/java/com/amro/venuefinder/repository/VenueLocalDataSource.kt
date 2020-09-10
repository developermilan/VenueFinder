package com.amro.venuefinder.repository

import android.app.Application
import com.amro.venuefinder.data.*
import com.amro.venuefinder.database.VenueDatabase

class VenueLocalDataSource(app: Application) : VenueDataSource {

    private val dao = VenueDatabase.getDatabase(app).venueDao()

    override suspend fun search(
        near: String,
        limit: Int,
        radius: Int,
        clientId: String,
        clientSecret: String,
        version: Int
    ): VenueSearchResult? {
        val venueList = dao.getVenueList()
        return VenueSearchResult(VenueSearchResponse(venueList))
    }

    override suspend fun details(
        id: String,
        clientId: String,
        clientSecret: String,
        version: Int
    ): VenueDetailResult? {
        return VenueDetailResult(VenueDetailResponse(dao.getVenueDetails(id = id)))
    }

    suspend fun insert(venues: List<Venue>) {
        dao.insertAll(venues)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    companion object {

        @Volatile
        private var INSTANCE: VenueLocalDataSource? = null

        fun getInstance(app: Application): VenueLocalDataSource {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = VenueLocalDataSource(app)
                INSTANCE = instance
                return instance
            }
        }
    }
}