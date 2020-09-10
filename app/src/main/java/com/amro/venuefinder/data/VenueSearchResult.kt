package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VenueSearchResult(
    @Expose
    @SerializedName("response")
    val searchResponse: VenueSearchResponse? = null
)