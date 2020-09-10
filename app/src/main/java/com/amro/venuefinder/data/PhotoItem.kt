package com.amro.venuefinder.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotoItem(
    @Expose
    @SerializedName("prefix")
    val prefix: String? = null,

    @Expose
    @SerializedName("suffix")
    val suffix: String? = null,

    @Expose
    @SerializedName("width")
    val width: Int? = null,

    @Expose
    @SerializedName("height")
    val height: Int? = null,
)