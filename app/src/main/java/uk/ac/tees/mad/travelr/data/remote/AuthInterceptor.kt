package uk.ac.tees.mad.travelr.data.remote

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import uk.ac.tees.mad.travelr.data.remote.auth.TokenRepository


// because we need the custom interceptor
class AuthInterceptor(

    private val tokenRepository: TokenRepository,
    private val clientId: String,
    private val clientSecret: String

//    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenRepository.getToken(clientId, clientSecret)
        }
        Log.d("Token: ", "intercept: $token ")
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

}
