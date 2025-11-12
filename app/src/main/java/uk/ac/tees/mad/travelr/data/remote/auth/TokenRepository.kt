package uk.ac.tees.mad.travelr.data.remote.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenRepository
@Inject constructor(
    private val authService: AuthService,
    @ApplicationContext private val context: Context
) {

    // check for the valid token if it exist
    // if not generate the token and store the token


    // to store small amount of data
    private val prefs = context.getSharedPreferences("amadeus_prefs", Context.MODE_PRIVATE)

    //Access token in shared pref
    var accessToken: String?
        get() = prefs.getString("access_token", null)
        set(value) = prefs.edit().putString("access_token", value).apply()

    private var expiryTime: Long
        get() = prefs.getLong("expiry_time", 0)
        set(value) = prefs.edit().putLong("expiry_time", value).apply()


    // to get a valid token
    suspend fun getToken(clientId: String, clientSecret: String): String {
        val currentTime = System.currentTimeMillis()

        // is token is not there or the time is expired
        if (accessToken == null || currentTime >= expiryTime) {
            val response = authService.getAccessToken(
                clientId = clientId,
                clientSecret = clientSecret
            )

            if (response.isSuccessful) {
                val tokenData = response.body()!!
                accessToken = tokenData.token
                // before the last minute
                expiryTime = currentTime + (tokenData.expiresIn * 1000) - 6000
            } else {
                throw Exception("Failed to get token: ${response.errorBody()?.string()}")
            }
        }
        return accessToken!!
    }
}