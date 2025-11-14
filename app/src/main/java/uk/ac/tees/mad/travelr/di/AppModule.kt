package uk.ac.tees.mad.travelr.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.travelr.data.remote.AmadeusApiService
import uk.ac.tees.mad.travelr.data.remote.AuthInterceptor
import uk.ac.tees.mad.travelr.data.remote.auth.AuthService
import uk.ac.tees.mad.travelr.data.remote.auth.TokenRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private const val BASE_URL = "https://test.api.amadeus.com/v1/"

    //    private const val AUTH_TOKEN = "yPr3dSe4UlvCnpSYHAjeFbwAelvF"
    private const val CLIENT_ID = "nbaFY1dYp3acCX2pGjjCPRmHZueDpETl"
    private const val CLIENT_SECRET = "dvkfOW4PQ3qr6H95"

    @Provides
    @Singleton
    fun provideAuthService(): AuthService = Retrofit.Builder()
        .baseUrl("https://test.api.amadeus.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthService::class.java)


    @Provides
    @Singleton
    fun provideTokenRepository(
        authService: AuthService,
        @ApplicationContext context: Context
    ): TokenRepository = TokenRepository(authService, context)


    @Provides
    @Singleton
    fun provideOkHttpClient(tokenRepository: TokenRepository): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                AuthInterceptor(
                    tokenRepository = tokenRepository,
                    CLIENT_ID,
                    CLIENT_SECRET
                )
            )
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideAmadeusApiService(retrofit: Retrofit): AmadeusApiService {
        return retrofit.create(AmadeusApiService::class.java)
    }
}