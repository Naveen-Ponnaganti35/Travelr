package uk.ac.tees.mad.travelr.data

import retrofit2.Response
import uk.ac.tees.mad.travelr.data.models.cities.CitiesResponse
import uk.ac.tees.mad.travelr.data.remote.AmadeusApiService

class AmadeusRepository
    (
    private val api: AmadeusApiService
) {
    suspend fun getActivities(city: String): Response<CitiesResponse> =
        api.getActivities(keyword = city)


    suspend fun getDestinations(latitude: Double, longitude: Double) =
        api.getDestinations(latitude, longitude)
}