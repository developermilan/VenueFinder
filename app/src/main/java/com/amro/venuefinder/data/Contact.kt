package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Contact(
    @Expose
    @SerializedName("formattedPhone")
    val formattedPhone: String? = "N/A",

    @Expose
    @SerializedName("twitter")
    val twitter: String? = "N/A",

    @Expose
    @SerializedName("instagram")
    val instagram: String? = "N/A",

    @Expose
    @SerializedName("facebookUsername")
    val facebook: String? = "N/A"
)