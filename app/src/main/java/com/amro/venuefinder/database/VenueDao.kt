package com.amro.venuefinder.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amro.venuefinder.data.Venue

@Dao
interface VenueDao {

    @Query("SELECT * from venue_table")
    suspend fun getVenueList(): List<Venue>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<Venue>)

    @Query("DELETE from venue_table")
    suspend fun deleteAll()

    @Query("SELECT * from venue_table WHERE id == :id")
    suspend fun getVenueDetails(id: String): Venue
}