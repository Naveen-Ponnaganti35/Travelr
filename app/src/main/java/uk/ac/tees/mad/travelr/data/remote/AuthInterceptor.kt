package uk.ac.tees.mad.travelr.data.remote

import okhttp3.Interceptor
import okhttp3.Response


// because we need the custom interceptor
class AuthInterceptor(
    private val token: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }

}
