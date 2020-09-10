package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Photos(
    @Expose
    @SerializedName("groups")
    val groups: List<PhotoGroups>? = null
)