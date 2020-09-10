package com.amro.venuefinder.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "venue_table")
data class Venue(

    @Expose
    @SerializedName("id")
    @PrimaryKey
    val id: String,

    @Expose
    @SerializedName("name")
    val name: String? = "N/A",

    @Expose
    @SerializedName("contact")
    @Embedded
    val contact: Contact? = null,

    @Expose
    @SerializedName("location")
    @Ignore
    val location: Location? = null,

    @Expose
    @SerializedName("description")
    val description: String? = "N/A",

    @Expose
    @SerializedName("photos")
    @Ignore
    val photos: Photos?,

    @Expose
    @SerializedName("rating")
    val rating: Double? = 0.0
) {
    constructor(
        id: String,
        name: String?,
        contact: Contact?,
        description: String?,
        rating: Double?
    ) : this(id, name, contact, null, description, null, rating)
}