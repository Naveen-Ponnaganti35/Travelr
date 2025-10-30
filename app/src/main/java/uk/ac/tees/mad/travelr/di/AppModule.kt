package uk.ac.tees.mad.travelr.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.travelr.data.remote.AmadeusApiService
import uk.ac.tees.mad.travelr.data.remote.AuthInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    private const val BASE_URL = "https://test.api.amadeus.com/v1/"
    private const val AUTH_TOKEN = "rkK3EbqWZthxoRHmBdIs5dv90i0X"
    private const val CLIENT_ID = "KvXH4B5pJsKGJid9IhwLDnrru1risAyo"
    private const val CLIENT_SECRET = "faXbdB6CCVIBybt3"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient=
        OkHttpClient.Builder()
            .addInterceptor (AuthInterceptor(AUTH_TOKEN))
            .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit{
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