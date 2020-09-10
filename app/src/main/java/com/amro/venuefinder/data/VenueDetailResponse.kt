package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VenueDetailResponse(
    @Expose
    @SerializedName("venue")
    val venue: Venue
)