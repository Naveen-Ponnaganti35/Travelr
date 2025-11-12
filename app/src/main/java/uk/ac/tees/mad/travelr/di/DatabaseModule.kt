package uk.ac.tees.mad.travelr.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.travelr.data.local.AppDatabase
import uk.ac.tees.mad.travelr.data.local.ItineraryDao
import uk.ac.tees.mad.travelr.data.local.ItineraryRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "travel_db"
        ).build()
    }

    @Provides
    fun provideItineraryDao(db: AppDatabase): ItineraryDao {
        return db.itineraryDao()
    }

    @Provides
    @Singleton
    fun providesItineraryRepository(dao: ItineraryDao): ItineraryRepository {
        return ItineraryRepository(dao)
    }
}