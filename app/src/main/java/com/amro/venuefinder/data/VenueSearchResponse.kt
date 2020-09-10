package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VenueSearchResponse(
    @Expose
    @SerializedName("venues")
    val venueList: List<Venue>? = null
)