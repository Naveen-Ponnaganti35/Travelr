package uk.ac.tees.mad.travelr

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.travelr.data.AmadeusRepository
import uk.ac.tees.mad.travelr.data.local.AppDatabase
import uk.ac.tees.mad.travelr.data.local.ItineraryRepository
import uk.ac.tees.mad.travelr.data.models.user.ProfileRepository
import uk.ac.tees.mad.travelr.data.remote.AmadeusApiService
import uk.ac.tees.mad.travelr.data.remote.AuthInterceptor
import uk.ac.tees.mad.travelr.data.remote.auth.AuthService
import uk.ac.tees.mad.travelr.data.remote.auth.TokenRepository

class AppContainer(context: Context) {

    private val authService: AuthService =
        Retrofit.Builder()
            .baseUrl("https://test.api.amadeus.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)

    private val tokenRepository = TokenRepository(authService, context)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            AuthInterceptor(
                tokenRepository,
                "BkIwCXBqOU0rwOKXszAO9fYWFvFAdnyh",
                "qYaQtsNhocw1Q8BV"
            )
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://test.api.amadeus.com/v1/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val amadeusApi =
        retrofit.create(AmadeusApiService::class.java)

    val amadeusRepository = AmadeusRepository(amadeusApi)

    private val database =
        Room.databaseBuilder(context, AppDatabase::class.java, "travel_db")
            .build()

    val itineraryRepository =
        ItineraryRepository(database.itineraryDao())

    val profileRepository =
        ProfileRepository(database.userProfileDao())
}
