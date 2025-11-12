package uk.ac.tees.mad.travelr.data.remote.auth

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("v1/security/oauth2/token")
    suspend fun getAccessToken(
        @Field("grant_type") grantType: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Response<AuthToken>
}

data class AuthToken(
    @SerializedName("access_token") val token: String,
    @SerializedName("expires_in") val expiresIn: Long
)
