package com.amro.venuefinder.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amro.venuefinder.data.Venue

@Database(entities = [Venue::class], version = 1, exportSchema = false)
public abstract class VenueDatabase : RoomDatabase() {

    abstract fun venueDao(): VenueDao

    companion object {

        @Volatile
        private var INSTANCE: VenueDatabase? = null

        fun getDatabase(context: Context): VenueDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VenueDatabase::class.java,
                    "venue_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}