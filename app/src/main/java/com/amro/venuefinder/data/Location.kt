package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(
    @Expose
    @SerializedName("city")
    val city: String? = "N/A",

    @Expose
    @SerializedName("state")
    val state: String? = "N/A",

    @Expose
    @SerializedName("formattedAddress")
    val address: List<String>? = null
)

