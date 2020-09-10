package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotoGroups(
    @Expose
    @SerializedName("items")
    val items: List<PhotoItem>? = null
)