package com.amro.venuefinder.repository

import com.amro.venuefinder.data.VenueDetailResult
import com.amro.venuefinder.data.VenueSearchResult
import com.amro.venuefinder.network.NetworkConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenueDataSource {

    @GET("/v2/venues/search")
    suspend fun search(
        @Query("near") near: String,
        @Query("limit") limit: Int = NetworkConfig.limit,
        @Query("radius") radius: Int = NetworkConfig.radius,
        @Query("client_id") clientId: String = NetworkConfig.clientID,
        @Query("client_secret") clientSecret: String = NetworkConfig.clientSecret,
        @Query("v") version: Int = NetworkConfig.version
    ): VenueSearchResult?

    @GET("/v2/venues/{venueId}")
    suspend fun details(
        @Path("venueId") id: String,
        @Query("client_id") clientId: String = NetworkConfig.clientID,
        @Query("client_secret") clientSecret: String = NetworkConfig.clientSecret,
        @Query("v") version: Int = NetworkConfig.version
    ): VenueDetailResult?
}