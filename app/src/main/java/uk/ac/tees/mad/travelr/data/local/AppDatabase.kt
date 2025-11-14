package uk.ac.tees.mad.travelr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.travelr.data.local.user.UserProfileDao
import uk.ac.tees.mad.travelr.data.models.user.UserProfileEntity


@Database(entities = [ItineraryEntity::class, UserProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itineraryDao(): ItineraryDao

    abstract fun userProfileDao(): UserProfileDao
}