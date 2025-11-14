package uk.ac.tees.mad.travelr.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.travelr.data.local.AppDatabase
import uk.ac.tees.mad.travelr.data.local.ItineraryDao
import uk.ac.tees.mad.travelr.data.local.ItineraryRepository
import uk.ac.tees.mad.travelr.data.local.user.UserProfileDao
import uk.ac.tees.mad.travelr.data.models.user.ProfileRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    fun provideUserProfileDao(db: AppDatabase): UserProfileDao {
        return db.userProfileDao()
    }

    // Repositories
    @Provides
    @Singleton
    fun provideProfileRepository(dao: UserProfileDao): ProfileRepository {
        return ProfileRepository(dao)
    }


    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

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