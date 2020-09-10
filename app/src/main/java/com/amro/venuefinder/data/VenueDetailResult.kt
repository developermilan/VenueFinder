package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VenueDetailResult(
    @Expose
    @SerializedName("response")
    val response: VenueDetailResponse
)