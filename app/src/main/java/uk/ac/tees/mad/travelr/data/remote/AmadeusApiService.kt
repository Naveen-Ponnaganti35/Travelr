package uk.ac.tees.mad.travelr.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.travelr.data.models.cities.CitiesResponse
import uk.ac.tees.mad.travelr.data.models.destinations.DestinationResponse

interface AmadeusApiService {

    @GET("reference-data/locations/cities")
    suspend fun getActivities(
        @Query("keyword") keyword: String, // ?keyword =
    ): Response<CitiesResponse>

    @GET("shopping/activities")
    suspend fun getDestinations(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ):Response<DestinationResponse>

}