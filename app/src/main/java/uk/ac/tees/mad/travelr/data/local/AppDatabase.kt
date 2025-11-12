package uk.ac.tees.mad.travelr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ItineraryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao
}